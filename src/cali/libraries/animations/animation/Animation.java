package cali.libraries.animations.animation;

import cali.commons.source.Source;

/**
 * Created by Aaron on 17.11.17.
 */
public class Animation {

    private String id;

    private Source[] sources;
    private Sampler sampler;
    private Channel channel;

    public Animation(String id, Source[] sources, Sampler sampler, Channel channel) {
        this.id = id;
        this.sources = sources;
        this.sampler = sampler;
        this.channel = channel;
    }

    public String getId() {
        return id;
    }

    public Source[] getSources() {
        return sources;
    }

    public Sampler getSampler() {
        return sampler;
    }

    public Channel getChannel() {
        return channel;
    }
}
