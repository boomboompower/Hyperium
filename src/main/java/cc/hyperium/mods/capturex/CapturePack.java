/*
 * Hyperium Client, Free client with huds and popular mod
 *     Copyright (C) 2018  Hyperium Dev Team
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package cc.hyperium.mods.capturex;

import cc.hyperium.mods.capturex.render.FrameRenderer;
import net.minecraft.client.shader.Framebuffer;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.Queue;

public class CapturePack {
    private Long timestamp;
    private Queue<Framebuffer> framebufferQueue;
    CapturePack(final Queue<Framebuffer> framebufferQueue){
        this.framebufferQueue = framebufferQueue;
        this.timestamp = System.currentTimeMillis();
    }

    public void renderFrames() {
        for (int i = 0; i < framebufferQueue.size(); i++) {
            FrameRenderer.render(timestamp, i, framebufferQueue.poll());
        }
    }
    public void cleanup(){
        //noinspection ResultOfMethodCallIgnored
        Arrays.asList(Objects.requireNonNull(new File(CaptureCore.captureXDir, "kill-" + timestamp)
                .listFiles())).parallelStream().filter(f -> !f.getName().contains(".mp4")).forEach(File::delete);
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
