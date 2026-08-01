package net.threecrows.drehmal_archipelago.common.regions;

import org.joml.Matrix4f;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.threecrows.drehmal_archipelago.archipelago.regions.Edge;

public class RegionBorderRenderer {

    private static final Identifier TEXTURE = new Identifier("textures/misc/forcefield.png");
    private static final RenderLayer LAYER = RenderLayer.getEntityTranslucent(TEXTURE);

    private static final double RENDER_DISTANCE = 64.0; 
    private static final double FADE_DISTANCE = 8.0;   
    private static final double WALL_HEIGHT = 320.0;
    private static final double SEGMENT_LENGTH = 16.0; 
    private static final double TILE_SIZE = 4.0;    


    public static void render(WorldRenderContext context) {
        Vec3d camPos = context.camera().getPos();
        MatrixStack matrices = context.matrixStack();
        VertexConsumerProvider.Immediate consumers = (VertexConsumerProvider.Immediate) context.consumers();
        if (consumers == null) return;

        VertexConsumer buffer = consumers.getBuffer(LAYER);
        long time = context.world().getTime();

        for (Edge seg : BorderState.getActiveSegments()) {
            double distSq = pointToSegmentDistSq(camPos.x, camPos.z, seg.getStart().x(), seg.getStart().z(), seg.getEnd().x(), seg.getEnd().z());
            if (distSq > RENDER_DISTANCE * RENDER_DISTANCE) continue;

            renderSegment(matrices, buffer, seg, camPos, time);
        }

        consumers.draw(LAYER);
    }

    private static void renderSegment(MatrixStack matrices, VertexConsumer buffer,
                                    Edge seg, Vec3d camPos, long time) {
        double sx = seg.getStart().x(), sz = seg.getStart().z();
        double ex = seg.getEnd().x(), ez = seg.getEnd().z();

        double fullLength = Math.sqrt((ex - sx) * (ex - sx) + (ez - sz) * (ez - sz));
        double dirX = (ex - sx) / fullLength;
        double dirZ = (ez - sz) / fullLength;

        float uScroll = (time % 1024) / 128.0f;
        int light = LightmapTextureManager.pack(15, 15);
        float r = 1.0f, g = 0.1f, b = 0.1f, a = 0.7f;

        Matrix4f matrix = matrices.peek().getPositionMatrix();

        int chunkCount = (int) Math.ceil(fullLength / SEGMENT_LENGTH);
        for (int i = 0; i < chunkCount; i++) {
            double startDist = i * SEGMENT_LENGTH;
            double endDist = Math.min(startDist + SEGMENT_LENGTH, fullLength);

            double cx1 = sx + dirX * startDist - camPos.x;
            double cz1 = sz + dirZ * startDist - camPos.z;
            double cx2 = sx + dirX * endDist - camPos.x;
            double cz2 = sz + dirZ * endDist - camPos.z;

            double y0 = 0 - camPos.y;
            double y1 = WALL_HEIGHT - camPos.y;

            // UV is local to this chunk, anchored to absolute distance along the edge so tiling stays continuous
            float u0 = uScroll + (float) (startDist / TILE_SIZE);
            float u1 = uScroll + (float) (endDist / TILE_SIZE);
            float v0 = 0f;
            float v1 = (float) ((y1 - y0) / TILE_SIZE);

            quadVertex(buffer, matrix, cx1, y0, cz1, u0, v1, r, g, b, a, light);
            quadVertex(buffer, matrix, cx2, y0, cz2, u1, v1, r, g, b, a, light);
            quadVertex(buffer, matrix, cx2, y1, cz2, u1, v0, r, g, b, a, light);
            quadVertex(buffer, matrix, cx1, y1, cz1, u0, v0, r, g, b, a, light);
        }
    }

    private static void quadVertex(VertexConsumer buffer, Matrix4f matrix,
                                    double x, double y, double z, float u, float v,
                                    float r, float g, float b, float a, int light) {
        buffer.vertex(matrix, (float) x, (float) y, (float) z)
              .color(r, g, b, a)
              .texture(u, v)
              .overlay(OverlayTexture.DEFAULT_UV)
              .light(light)
              .normal(0, 1, 0)
              .next();
    }

    private static double pointToSegmentDistSq(double px, double pz, double x1, double z1, double x2, double z2) {
        double dx = x2 - x1, dz = z2 - z1;
        double lenSq = dx * dx + dz * dz;
        double t = lenSq == 0 ? 0 : ((px - x1) * dx + (pz - z1) * dz) / lenSq;
        t = Math.max(0, Math.min(1, t));
        double cx = x1 + t * dx, cz = z1 + t * dz;
        double ex = px - cx, ez = pz - cz;
        return ex * ex + ez * ez;
    }
}
