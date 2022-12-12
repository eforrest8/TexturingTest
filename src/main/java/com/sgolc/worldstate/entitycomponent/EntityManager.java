package com.sgolc.worldstate.entitycomponent;

import java.io.*;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Manages large collections of entities.
 * Supports querying and transactional updates.
 * Is thread-safe.
 */
public class EntityManager implements Externalizable {
    private static final int serialVersionId = 1;
    private static final int serialMagicNumber = 0x5601C000;

    private final List<List<Component>> entities = new ArrayList<>();
    private final Map<Class<? extends Component>, NavigableMap<Component, Integer>> indices = new HashMap<>(20);

    public EntityManager() {}

    public void addComponentToEntity(Entity entity, Component component) {
        addComponentToEntity(entity.id(), component);
    }

    public Entity createEntity() {
        return createEntity(new Component[0]);
    }

    public Entity createEntity(Component... componentList) {
        List<Component> newlist = new LinkedList<>(List.of(componentList));
        entities.add(newlist);
        newlist.forEach(c -> updateIndex(entities.size()-1, c));
        return new Entity(entities.size()-1, componentList);
    }

    private void addComponentToEntity(int id, Component component) {
        entities.get(id).add(component);
        updateIndex(id, component);
    }

    private void updateIndex(int id, Component component) {
        if (!indices.containsKey(component.getClass())) {
            createIndex(component.getClass());
        }
        indices.get(component.getClass()).put(component, id);
    }

    void createIndex(Class<? extends Component> componentClass) {
        indices.put(componentClass, new TreeMap<>());
    }

    public List<Entity> computeQuery(Query query) {
        Iterator<QueryPart> iterator = Arrays.stream(query.parts()).iterator();
        QueryPart currentPart = iterator.next();
        Collection<Integer> resultIndices = computeQueryPart(currentPart);
        while (iterator.hasNext()) {
            currentPart = iterator.next();
            resultIndices = currentPart.mergeMode.merge.apply(resultIndices, computeQueryPart(currentPart));
        }
        return resultIndices.stream().map(this::getEntityById).toList();
    }

    private List<Integer> computeQueryPart(QueryPart part) {
        return indices.get(part.componentClass())
                .entrySet()
                .stream()
                .filter(e -> part.predicate().test(e.getKey()))
                .map(Map.Entry::getValue)
                .toList();
    }

    private Entity getEntityById(int id) {
        return new Entity(id, entities.get(id).toArray(new Component[0]));
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(serialMagicNumber);
        out.writeInt(serialVersionId);
        out.writeObject(entities);
        out.flush();
        out.close();
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        boolean magicMatches = in.readInt() == serialMagicNumber;
        boolean versionMatches = in.readInt() == serialVersionId;
        if (!(magicMatches && versionMatches)) {
            throw new IOException("Unable to deserialize; " +
                    (versionMatches ? "incorrect magic number." : "version mismatch."));
        }
        Object loaded = in.readObject();
        entities.clear();
        if (loaded instanceof List<?> outer) {
            for (Object o : outer) {
                if (o instanceof List<?> inner) {
                    List<Component> newInner = new ArrayList<>();
                    for (Object innerObj : inner) {
                        if (innerObj instanceof Component component) {
                            newInner.add(component);
                        }
                    }
                    entities.add(newInner);
                }
            }
            rebuildIndices();
        }
    }

    private void rebuildIndices() {
        indices.clear();
        Iterator<List<Component>> entityIterator = entities.listIterator();
        for (int i = 0; entityIterator.hasNext(); i++) {
            int finalI = i;
            entityIterator.next().forEach(e -> addComponentToEntity(finalI, e));
        }
    }

    public record Query(QueryPart... parts) {}

    public record QueryPart(
            Class<? extends Component> componentClass,
            Predicate<Component> predicate,
            ResultMergeMode mergeMode) {}

    public enum ResultMergeMode {
        AND((a, b) -> Stream.of(a, b)
                .mapMulti(Iterable<Integer>::forEach)
                .distinct()
                .filter(e -> Boolean.logicalAnd(a.contains(e), b.contains(e)))
                .toList()),
        OR((a, b) -> Stream.of(a, b)
                .mapMulti(Iterable<Integer>::forEach)
                .distinct()
                .toList()),
        XOR((a, b) -> Stream.of(a, b)
                    .mapMulti(Iterable<Integer>::forEach)
                    .distinct()
                    .filter(e -> Boolean.logicalXor(a.contains(e), b.contains(e)))
                    .toList());

        final BinaryOperator<Collection<Integer>> merge;

        ResultMergeMode(BinaryOperator<Collection<Integer>> func) {
            merge = func;
        }
    }
}
