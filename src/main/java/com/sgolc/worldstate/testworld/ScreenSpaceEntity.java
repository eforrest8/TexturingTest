package com.sgolc.worldstate.testworld;

import com.sgolc.graphicsmodel.MappedTexture;
import com.sgolc.graphicsmodel.coordinates.CoordinateMapper;
import com.sgolc.graphicsmodel.coordinates.MultiMapper;
import com.sgolc.graphicsmodel.texture.SolidColorTexture;
import com.sgolc.graphicsmodel.texture.Texture;
import com.sgolc.worldstate.entitycomponent.Entity;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class ScreenSpaceEntity extends Entity {

    private TranslateComponent translate = new TranslateComponent(0,0);
    private ScaleComponent scale = new ScaleComponent(1, 1);
    private RotateComponent rotate = new RotateComponent(0);
    private TextureComponent texture = new TextureComponent(new SolidColorTexture(Color.MAGENTA));
    private ZIndexComponent zIndex = new ZIndexComponent(0);

    public double[] getTranslate() {
        return new double[]{ translate.x(), translate.y()};
    }

    public double[] getScale() {
        return new double[] {scale.x(), scale.y()};
    }

    public double getRotate() {
        return rotate.angle;
    }

    public Texture getTexture() {
        return texture.texture();
    }

    public int getzIndex() {
        return zIndex.zIndex();
    }

    public void setTranslate(double dx, double dy) {
        components.remove(translate);
        translate = new TranslateComponent(dx, dy);
        components.add(translate);
    }

    public void setScale(double xScale, double yScale) {
        components.remove(scale);
        scale = new ScaleComponent(xScale, yScale);
        components.add(scale);
    }

    public void setRotate(double angle) {
        components.remove(rotate);
        rotate = new RotateComponent(angle);
        components.add(rotate);
    }

    public void setTexture(Texture tex) {
        components.remove(texture);
        texture = new TextureComponent(tex);
        components.add(texture);
    }

    public void setzIndex(int z) {
        components.remove(zIndex);
        zIndex = new ZIndexComponent(z);
        components.add(zIndex);
    }

    public ScreenSpaceEntity() {
        this.components.addAll(List.of(translate, scale, texture, zIndex));
    }

    private MultiMapper getMultiMapper() {
        return new MultiMapper(components.stream()
                .map(c -> c instanceof Mapper m ? m : null)
                .filter(Objects::nonNull)
                .map(Mapper::getMapper)
                .toArray(CoordinateMapper[]::new));
    }

    public Texture getMappedTexture() {
        return new MappedTexture(getTexture(), getMultiMapper());
    }
}
