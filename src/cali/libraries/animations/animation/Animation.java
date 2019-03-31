package cali.libraries.animations.animation;

import cali.commons.source.Source;
import cali.interfaces.Identifiable;

/**
 * Created by Aaron on 17.11.17.
 */
public class Animation implements Identifiable {

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

    @Override
    public String getSemantic() {
        return Identifiable.NO_SEMANTIC;
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
