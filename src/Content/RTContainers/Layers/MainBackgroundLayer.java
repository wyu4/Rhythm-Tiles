package Content.RTContainers.Layers;

import Content.RTContainers.Layers.Templates.AlphaGradientLayer;

import java.awt.*;

public class MainBackgroundLayer extends AlphaGradientLayer {
    /**
     * Creates a main background layer preset
     */
    public MainBackgroundLayer() {
        setAlpha(0.5f);
        setGradientColor1(
                new Color(0, 13, 255)
        );
        setGradientColor2(
                new Color(0, 255, 224)
        );
    }
}
