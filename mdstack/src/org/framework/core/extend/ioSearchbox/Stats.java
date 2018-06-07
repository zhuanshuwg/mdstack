package org.framework.core.extend.ioSearchbox;

import io.searchbox.action.AbstractAction;
import io.searchbox.action.GenericResultAbstractAction;

/**
 * @author wangguan
 */
public class Stats extends GenericResultAbstractAction {

    public Stats(Builder builder) {
        super(builder);
        setURI(buildURI());
    }

    @Override
    protected String buildURI() {
        StringBuilder sb = new StringBuilder(super.buildURI());
        sb.append("/_cluster/stats/");
        return sb.toString();
    }

    @Override
    public String getRestMethodName() {
        return "GET";
    }

    public static class Builder extends AbstractAction.Builder<Stats, Builder> {

        @Override
        public Stats build() {
            return new Stats(this);
        }
    }

}
