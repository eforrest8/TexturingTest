package com.sgolc.worldstate.entitycomponent;

import java.io.*;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Manages large collections of entities.
 * Supports querying and transactional updates.
 * Is thread-safe.
 * Or at least these are the things I want this to be when it's done. :P
 */
public class EntityStore implements Externalizable {
    private static final int serialVersionId = 1;
    private static final int serialMagicNumber = 0x5601C000;

    private final Map<Integer, List<Component>> entities = Collections.synchronizedMap(new TreeMap<>());
    private final Map<Class<? extends Component>, List<Integer>> indices = Collections.synchronizedMap(new HashMap<>(20));
    private final Random keyGenerator = new Random();

    public EntityStore() {}

    public void addComponentToEntity(Entity entity, Component component) {
        addComponentToEntity(entity.id(), component);
    }

    private void addComponentToEntity(int id, Component component) {
        entities.get(id).add(component);
        updateIndex(id, component);
    }

    public void replaceComponent(Entity entity, UnaryOperator<Component> operator) {
        List<Component> componentList = entities.get(entity.id());
        componentList.replaceAll(operator);
    }

    public Entity createEntity(Component... componentList) {
        List<Component> newlist = new LinkedList<>(List.of(componentList));
        int key = generateKey();
        entities.put(key, newlist);
        newlist.forEach(c -> updateIndex(key, c));
        return new Entity(key, this, componentList);
    }

    private int generateKey() {
        int possible;
        do {
            possible = keyGenerator.nextInt();
        } while (entities.containsKey(possible));
        return possible;
    }

    private void updateIndex(int id, Component component) {
        if (!indices.containsKey(component.getClass())) {
            createIndex(component.getClass());
        }
        indices.get(component.getClass()).add(id);
    }

    private void createIndex(Class<? extends Component> componentClass) {
        indices.put(componentClass, new LinkedList<>());
    }

    public List<Entity> computeQuery(Query query) {
        Iterator<QueryPart<?>> iterator = Arrays.stream(query.parts()).iterator();
        QueryPart<?> currentPart = iterator.next();
        Collection<Integer> resultIndices = computeQueryPart(currentPart);
        while (iterator.hasNext()) {
            currentPart = iterator.next();
            resultIndices = currentPart.mergeMode.merge.apply(resultIndices, computeQueryPart(currentPart));
        }
        return resultIndices.stream().parallel().map(this::getEntityById).toList();
    }

    private List<Integer> computeQueryPart(QueryPart<?> part) {
        return indices.getOrDefault(part.componentClass(), List.of())
                .parallelStream()
                .filter(e -> entities.get(e).stream().parallel().anyMatch((part)))
                .toList();
    }

    private Entity getEntityById(int id) {
        return new Entity(id, this, entities.get(id).toArray(new Component[0]));
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
                    entities.put(generateKey(), newInner);
                }
            }
            rebuildIndices();
        }
    }

    private void rebuildIndices() {
        indices.clear();
        Iterator<Map.Entry<Integer, List<Component>>> entityIterator = entities.entrySet().iterator();
        entityIterator.forEachRemaining(e -> e.getValue().forEach(c -> addComponentToEntity(e.getKey(), c)));
    }

    public record Query(QueryPart<?>... parts) {}

    public record QueryPart<T extends Component>(
            Class<T> componentClass,
            Predicate<T> predicate,
            ResultMergeMode mergeMode) implements Predicate<Component> {

        @SuppressWarnings("unchecked")
        @Override
        public boolean test(Component component) {
            return componentClass.isInstance(component) && predicate().test((T)component);
        }
    }

    @SuppressWarnings("RedundantTypeArguments")
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

        public final BinaryOperator<Collection<Integer>> merge;

        ResultMergeMode(BinaryOperator<Collection<Integer>> func) {
            merge = func;
        }
    }
}
