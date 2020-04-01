package layout;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class TrainingStatsLayout implements LayoutManager2 {

    Map<Component, TrainingStatsConstraint> container;
    int preferredHeight, preferredWidth;
    int insetHeight, insetWidth;
    int freeHeight, freeWidth;

    public TrainingStatsLayout() {
        container = new LinkedHashMap<>();
    }


    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        if (constraints != null && constraints.getClass() == TrainingStatsConstraint.class) {
            container.put(comp, (TrainingStatsConstraint) constraints);
        } else {
            container.put(comp, new TrainingStatsConstraint());
        }
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
            return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    @Override
    public void invalidateLayout(Container target) {

    }

    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {

    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        int width = 0;
        int height = 0;
        for (Map.Entry<Component, TrainingStatsConstraint> entry : this.container.entrySet()) {
            if (entry.getKey().getPreferredSize().width > width) {
                width = entry.getKey().getPreferredSize().width;
            }
            height += entry.getKey().getPreferredSize().height;
        }
        Dimension preferredSize = new Dimension(width + this.getInsetWidth(),
                height + this.getInsetHeight());
        return preferredSize;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        Dimension minimumSize = new Dimension(this.getMinimumWidth() + this.getInsetWidth(),
                this.getMinimumHeight() + this.getInsetHeight());
        return minimumSize;
    }

    @Override
    public void layoutContainer(Container parent) {
        this.preferredWidth = this.getPreferredWidth();
        this.preferredHeight = this.getPreferredHeight();
        this.insetHeight = this.getInsetHeight();
        this.freeHeight = this.getFreeHeight(parent.getSize());

        this.setHeights(parent.getSize());
        this.setWidths(parent.getSize());
    }


    private void setHeights(Dimension parentDimension) {
        int top = 0;
        int height = parentDimension.height;

        for (Map.Entry<Component, TrainingStatsConstraint> entry : container.entrySet()) {
            if (!entry.getValue().fixed) {
                top += entry.getValue().insets.top;
                Dimension size = entry.getKey().getSize();
                if (entry.getValue().fillHeight) {
                    if ((this.preferredHeight + this.insetHeight) < height) {
                        size.height = this.freeHeight;
                    } else {
                        size.height = entry.getKey().getMinimumSize().height;
                    }
                } else {
                    if ((this.preferredHeight + this.insetHeight) < height) {
                        size.height = entry.getKey().getPreferredSize().height;
                    } else {
                        size.height = entry.getKey().getPreferredSize().height;
                    }
                }
                entry.getKey().setSize(size);
                entry.getKey().setLocation(entry.getKey().getX(), top);
                top += size.height + entry.getValue().insets.bottom;
            } else {
                Dimension size = entry.getKey().getPreferredSize();
                entry.getKey().setSize(size);
                if (entry.getValue().alignment == Alignment.NORTHEAST) {
                    entry.getKey().
                            setLocation(entry.getKey().getX(), 0 + entry.getValue().insets.top);
                }
                if (entry.getValue().alignment == Alignment.NORTHWEST) {
                    entry.getKey()
                            .setLocation(entry.getValue().insets.left, entry.getValue().insets.top);
                }
            }
        }

    }

    private void setWidths(Dimension parentDimension) {
        int width = parentDimension.width;
        for (Map.Entry<Component, TrainingStatsConstraint> entry : container.entrySet()) {
            if (!entry.getValue().fixed) {
                Dimension size = entry.getKey().getSize();
                if (entry.getValue().fillWidth) {
                    if (this.preferredWidth < width) {
                        size.width = width - entry.getValue().insets.left - entry.getValue().insets.right;
                    } else {
                        size.width = entry.getKey().getMinimumSize().width;
                    }
                } else {
                    size.width = entry.getKey().getPreferredSize().width;
                }
                entry.getKey().setSize(size);
                if (entry.getValue().alignment == Alignment.CENTER) {
                    int x = (int) ((width - entry.getKey().getPreferredSize().getWidth()) / 2);
                    entry.getKey().setLocation(x, entry.getKey().getY());

                } else if (entry.getValue().alignment == Alignment.WEST) {
                    entry.getKey().setLocation(entry.getValue().insets.left, entry.getKey().getY());
                }
            } else {
                Dimension size = entry.getKey().getPreferredSize();
                entry.getKey().setSize(size);
                if (entry.getValue().alignment == Alignment.NORTHEAST) {
                    entry.getKey().
                            setLocation(width - size.width - entry.getValue().insets.right, entry.getKey().getY());
                }
            }
        }
    }

    private int getInsetWidth() {
        int inset = 0;
        for (Map.Entry<Component, TrainingStatsConstraint> entry : this.container.entrySet()) {
            if (!entry.getValue().fixed) {
                int sum = entry.getValue().insets.left + entry.getValue().insets.right;
                if (sum > inset) {
                    inset = sum;
                }
            }
        }
        return inset;
    }

    private int getInsetHeight() {
        int inset = 0;
        for (Map.Entry<Component, TrainingStatsConstraint> entry : this.container.entrySet()) {
            inset += entry.getValue().insets.bottom + entry.getValue().insets.top;
        }
        return inset;
    }

    private int getMinimumHeight() {
        int minimumHeight = 0;
        for (Map.Entry<Component, TrainingStatsConstraint> entry : this.container.entrySet()) {
            if (!entry.getValue().fixed) {
                int min = entry.getKey().getMinimumSize().height;
                minimumHeight += min;
            }
        }
        return minimumHeight;
    }

    private int getPreferredHeight() {
        int preferredHeight = 0;
        for (Map.Entry<Component, TrainingStatsConstraint> entry : this.container.entrySet()) {
            if (!entry.getValue().fixed) {
                int pref = 0;
                if (entry.getValue().fillHeight) {
                    pref = entry.getKey().getMinimumSize().height;
                } else {
                    pref = entry.getKey().getPreferredSize().height;
                }
                preferredHeight += pref;
            }
        }
        return preferredHeight;
    }

    private int getMinimumWidth() {
        int minimumWidth = 0;
        for (Map.Entry<Component, TrainingStatsConstraint> entry : this.container.entrySet()) {
            if (!entry.getValue().fixed) {
                int width = entry.getKey().getMinimumSize().width;
                if (width > minimumWidth) {
                    minimumWidth = width;
                }
            }
        }
        return minimumWidth;
    }

    private int getPreferredWidth() {
        int preferredWidth = 0;
        for (Map.Entry<Component, TrainingStatsConstraint> entry : this.container.entrySet()) {
            if (!entry.getValue().fixed) {
                int width = 0;
                if (entry.getValue().fillWidth) {
                    width = entry.getKey().getMinimumSize().width;
                } else {
                    width = entry.getKey().getPreferredSize().width;
                }
                if (width > preferredWidth) {
                    preferredWidth = width;
                }
            }
        }
        return preferredWidth;
    }

    private int getFreeHeight(Dimension parentDimension) {
        int freeHeight = parentDimension.height - this.preferredHeight - this.insetHeight;
        for (Map.Entry<Component, TrainingStatsConstraint> entry : this.container.entrySet()) {
            if (!entry.getValue().fixed) {
                if (entry.getValue().fillHeight) {
                    freeHeight += entry.getKey().getMinimumSize().height;
                }
            }
        }


        return freeHeight;
    }

    public Dimension getMinSize() {
        int width = this.getPreferredWidth() + this.getInsetWidth();
        int height = this.getPreferredHeight() + this.getInsetHeight();

        return new Dimension(width, height);
    }

}
