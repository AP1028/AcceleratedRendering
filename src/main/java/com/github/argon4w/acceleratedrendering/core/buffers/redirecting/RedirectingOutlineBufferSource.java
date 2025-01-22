package com.github.argon4w.acceleratedrendering.core.buffers.redirecting;

import com.github.argon4w.acceleratedrendering.core.buffers.IOutlineBufferSource;
import com.github.argon4w.acceleratedrendering.core.buffers.accelerated.IAcceleratedBufferSource;
import com.github.argon4w.acceleratedrendering.core.buffers.accelerated.IAcceleratedOutlineBufferSource;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;

public class RedirectingOutlineBufferSource extends MultiBufferSource.BufferSource implements IOutlineBufferSource {

    private final ObjectSet<IOutlineBufferSource> allBufferSources;
    private final ObjectSet<IAcceleratedOutlineBufferSource> bufferSources;
    private final ObjectSet<String> fallbackNames;
    private final IOutlineBufferSource fallbackBufferSource;

    public RedirectingOutlineBufferSource(
            ObjectSet<IAcceleratedOutlineBufferSource> bufferSources,
            ObjectSet<String> fallbackNames,
            IOutlineBufferSource fallbackBufferSource
    ) {
        super(null, null);
        this.bufferSources = bufferSources;
        this.fallbackNames = fallbackNames;
        this.fallbackBufferSource = fallbackBufferSource;

        this.allBufferSources = new ObjectArraySet<>();
        this.allBufferSources.addAll(bufferSources);
        this.allBufferSources.add(fallbackBufferSource);
    }

    @Override
    public void endBatch(RenderType pRenderType) {

    }

    @Override
    public void endBatch() {

    }

    @Override
    public void endLastBatch() {

    }

    @Override
    public void setColor(int color) {
        for (IOutlineBufferSource bufferSource : allBufferSources) {
            bufferSource.setColor(color);
        }
    }

    @Override
    public VertexConsumer getBuffer(RenderType pRenderType) {
        if (fallbackNames.contains(pRenderType.name)) {
            return fallbackBufferSource.getBuffer(pRenderType);
        }

        for (IAcceleratedBufferSource bufferSource1 : bufferSources) {
            if (bufferSource1
                    .getBufferEnvironment()
                    .isAccelerated(pRenderType.format)
            ) {
                return bufferSource1.getBuffer(pRenderType);
            }
        }

        return fallbackBufferSource.getBuffer(pRenderType);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final ObjectSet<IAcceleratedOutlineBufferSource> bufferSources;
        private final ObjectSet<String> fallbackNames;

        private IOutlineBufferSource fallbackBufferSource;

        private Builder() {
            this.bufferSources = new ObjectArraySet<>();
            this.fallbackNames = new ObjectOpenHashSet<>();
            this.fallbackBufferSource = null;
        }

        public Builder fallback(IOutlineBufferSource fallback) {
            this.fallbackBufferSource = fallback;
            return this;
        }

        public Builder bufferSource(IAcceleratedOutlineBufferSource source) {
            this.bufferSources.add(source);
            return this;
        }

        public Builder fallbackName(String name) {
            this.fallbackNames.add(name);
            return this;
        }

        public RedirectingOutlineBufferSource build() {
            return new RedirectingOutlineBufferSource(
                    bufferSources,
                    fallbackNames,
                    fallbackBufferSource
            );
        }
    }
}
