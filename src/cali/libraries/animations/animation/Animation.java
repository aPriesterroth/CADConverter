package cali.libraries.animations.animation;

import cali.commons.source.Source;

/**
 * Created by Aaron on 17.11.17.
 */
public class Animation {

    private Source[] sources;
    private Sampler[] samplers;
    private Channel channel;

    public Animation(Source[] sources, Sampler[] samplers, Channel channel) {
        this.sources = sources;
        this.samplers = samplers;
        this.channel = channel;
    }

    public Source[] getSources() {
        return sources;
    }

    public Sampler[] getSamplers() {
        return samplers;
    }

    public Channel getChannel() {
        return channel;
    }
}
