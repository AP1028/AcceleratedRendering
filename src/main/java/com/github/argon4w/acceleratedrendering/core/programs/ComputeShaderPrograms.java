package com.github.argon4w.acceleratedrendering.core.programs;

import com.github.argon4w.acceleratedrendering.AcceleratedRenderingModEntry;
import com.github.argon4w.acceleratedrendering.core.backends.programs.BarrierFlags;
import com.github.argon4w.acceleratedrendering.core.utils.ResourceLocationUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

@EventBusSubscriber(modid = AcceleratedRenderingModEntry.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ComputeShaderPrograms {

    public static final ResourceLocation CORE_BLOCK_VERTEX_TRANSFORM_KEY = ResourceLocationUtils.create("core_block_vertex_transform");
    public static final ResourceLocation CORE_ENTITY_VERTEX_TRANSFORM_KEY = ResourceLocationUtils.create("core_entity_vertex_transform");
    public static final ResourceLocation CORE_POS_TEX_COLOR_VERTEX_TRANSFORM_KEY = ResourceLocationUtils.create("core_pos_tex_color_vertex_transform");
    public static final ResourceLocation CORE_POS_TEX_VERTEX_TRANSFORM_KEY = ResourceLocationUtils.create("core_pos_tex_vertex_transform");
    public static final ResourceLocation CORE_POS_COLOR_TEX_LIGHT_VERTEX_TRANSFORM_KEY = ResourceLocationUtils.create("core_pos_color_tex_light_vertex_transform");
    public static final ResourceLocation CORE_PASS_THROUGH_QUAD_CULLING_KEY = ResourceLocationUtils.create("core_pass_through_quad_culling");
    public static final ResourceLocation CORE_PASS_THROUGH_TRIANGLE_CULLING_KEY = ResourceLocationUtils.create("core_pass_through_triangle_culling");

    @SubscribeEvent
    public static void onLoadComputeShaders(LoadComputeShaderEvent event) {
        event.loadComputeShader(
                CORE_BLOCK_VERTEX_TRANSFORM_KEY,
                ResourceLocationUtils.create("shaders/core/transform/block_vertex_transform_shader.compute"),
                BarrierFlags.SHADER_STORAGE
        );

        event.loadComputeShader(
                CORE_ENTITY_VERTEX_TRANSFORM_KEY,
                ResourceLocationUtils.create("shaders/core/transform/entity_vertex_transform_shader.compute"),
                BarrierFlags.SHADER_STORAGE
        );

        event.loadComputeShader(
                CORE_POS_TEX_COLOR_VERTEX_TRANSFORM_KEY,
                ResourceLocationUtils.create("shaders/core/transform/pos_tex_color_vertex_transform_shader.compute"),
                BarrierFlags.SHADER_STORAGE
        );

        event.loadComputeShader(
                CORE_POS_TEX_VERTEX_TRANSFORM_KEY,
                ResourceLocationUtils.create("shaders/core/transform/pos_tex_vertex_transform_shader.compute"),
                BarrierFlags.SHADER_STORAGE
        );

        event.loadComputeShader(
                CORE_POS_COLOR_TEX_LIGHT_VERTEX_TRANSFORM_KEY,
                ResourceLocationUtils.create("shaders/core/transform/pos_color_tex_light_vertex_transform_shader.compute"),
                BarrierFlags.SHADER_STORAGE
        );

        event.loadComputeShader(
                CORE_PASS_THROUGH_QUAD_CULLING_KEY,
                ResourceLocationUtils.create("shaders/core/culling/pass_through_quad_culling_shader.compute"),
                BarrierFlags.SHADER_STORAGE,
                BarrierFlags.ATOMIC_COUNTER
        );

        event.loadComputeShader(
                CORE_PASS_THROUGH_TRIANGLE_CULLING_KEY,
                ResourceLocationUtils.create("shaders/core/culling/pass_through_triangle_culling_shader.compute"),
                BarrierFlags.SHADER_STORAGE,
                BarrierFlags.ATOMIC_COUNTER
        );
    }

    @SubscribeEvent
    public static void onRegisterResourceReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(ComputeShaderProgramLoader.INSTANCE);
    }
}
