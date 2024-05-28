package Content.RTComponents.Layers;

import Content.RTComponents.Layers.Templates.AlphaLayer;

/**
 * A layer with opacity 0
 */
public class InvisibleLayer extends AlphaLayer {
    /**
     * Creates an invisible layer
     */
    public InvisibleLayer() {
        setAlpha(0f);
    }
}
