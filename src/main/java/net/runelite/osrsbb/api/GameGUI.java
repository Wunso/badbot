package net.runelite.osrsbb.api;

import net.runelite.api.widgets.Widget;
import net.runelite.osrsbb.internal.globvals.WidgetIndices;
import net.runelite.osrsbb.internal.globvals.enums.InterfaceTab;
import net.runelite.osrsbb.internal.globvals.enums.ViewportLayout;

/**
 * For internal use to find GUI components.
 *
 * @author GigiaJ
 */
public class GameGUI  extends MethodProvider {
    public GameGUI(MethodContext ctx) {
        super(ctx);
    }

    /**
     * @return The compasses <code>Widget</code>;otherwise null.
     */
    public synchronized Widget getCompass() {
        ViewportLayout layout = getViewportLayout();
        if (layout != null) {
            switch (layout) {
                case FIXED_CLASSIC: return methods.client.getWidget(
                        WidgetIndices.FixedClassicViewport.GROUP_INDEX,
                        WidgetIndices.FixedClassicViewport.MINIMAP_COMPASS_SPRITE);
                case RESIZABLE_CLASSIC: return methods.client.getWidget(
                        WidgetIndices.ResizableClassicViewport.GROUP_INDEX,
                        WidgetIndices.ResizableClassicViewport.MINIMAP_COMPASS_SPRITE);
                case RESIZABLE_MODERN: return methods.client.getWidget(
                        WidgetIndices.ResizableModernViewport.GROUP_INDEX,
                        WidgetIndices.ResizableModernViewport.MINIMAP_COMPASS_SPRITE);
            }
        }
        return null;
    }

    /**
     * @return The minimap <code>Widget</code>; otherwise null.
     */
    public synchronized Widget getMinimap() {
        ViewportLayout layout = getViewportLayout();
        if (layout != null) {
            switch (layout) {
                case FIXED_CLASSIC: return methods.client.getWidget(
                        WidgetIndices.FixedClassicViewport.GROUP_INDEX,
                        WidgetIndices.FixedClassicViewport.MINIMAP_CONTAINER);
                case RESIZABLE_CLASSIC: return methods.client.getWidget(
                        WidgetIndices.ResizableClassicViewport.GROUP_INDEX,
                        WidgetIndices.ResizableClassicViewport.MINIMAP_CONTAINER);
                case RESIZABLE_MODERN: return methods.client.getWidget(
                        WidgetIndices.ResizableModernViewport.GROUP_INDEX,
                        WidgetIndices.ResizableModernViewport.MINIMAP_CONTAINER);
            };
        }
        return null;
    }

    /**
     * @param interfaceTab The enumerated tab containing WidgetInfo of the tab.
     * @return The specified tab <code>Widget</code>; otherwise null.
     */
    public synchronized Widget getTab(final InterfaceTab interfaceTab) {
        ViewportLayout layout = getViewportLayout();
        if (layout != null) {
            switch (layout) {
                case FIXED_CLASSIC: return interfaceTab.getFixedClassicWidget();
                case RESIZABLE_CLASSIC: return interfaceTab.getResizableClassicWidget();
                case RESIZABLE_MODERN: return interfaceTab.getResizableModernWidget();
            };
        }
        return null;
    }

    /**
     * Determines client viewport layout mode.
     *
     * @return <code>ViewportLayout</code>; otherwise <code>null</code>.
     */
    public ViewportLayout getViewportLayout() {
        Widget minimapOnFixedClassic = methods.client.getWidget(
                WidgetIndices.FixedClassicViewport.GROUP_INDEX,
                WidgetIndices.FixedClassicViewport.MINIMAP_COMPASS_SPRITE);
        Widget minimapOnResizableClassic = methods.client.getWidget(
                WidgetIndices.ResizableClassicViewport.GROUP_INDEX,
                WidgetIndices.ResizableClassicViewport.MINIMAP_COMPASS_SPRITE);
        Widget minimapOnResizableModern = methods.client.getWidget(
                WidgetIndices.ResizableModernViewport.GROUP_INDEX,
                WidgetIndices.ResizableModernViewport.MINIMAP_COMPASS_SPRITE);
        if (minimapOnFixedClassic != null)
            return ViewportLayout.FIXED_CLASSIC;
        else if (minimapOnResizableClassic != null)
            return ViewportLayout.RESIZABLE_CLASSIC;
        else if (minimapOnResizableModern != null)
            return ViewportLayout.RESIZABLE_MODERN;
        return null;
    }
}
