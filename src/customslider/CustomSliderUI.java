/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customslider;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 *
 * @author Daniel Casado
 */
public class CustomSliderUI extends BasicSliderUI {

        private static final int TRACK_HEIGHT = 6;
        private static final int TRACK_WIDTH = 6;
        private static final int TRACK_ARC = 5;
        private final Dimension THUMB_SIZE = new Dimension(15, 15);
        private final RoundRectangle2D.Float trackShape = new RoundRectangle2D.Float();

        public CustomSliderUI(final JSlider b) {
            super(b);
        }

        @Override
        protected void calculateTrackRect() {
            super.calculateTrackRect();
            if (isHorizontal()) {
                trackRect.y = trackRect.y + (trackRect.height - TRACK_HEIGHT) / 2;
                trackRect.height = TRACK_HEIGHT;
            } else {
                trackRect.x = trackRect.x + (trackRect.width - TRACK_WIDTH) / 2;
                trackRect.width = TRACK_WIDTH;
            }
            trackShape.setRoundRect(trackRect.x, trackRect.y, trackRect.width, trackRect.height, TRACK_ARC, TRACK_ARC);
        }

        @Override
        protected void calculateThumbLocation() {
            super.calculateThumbLocation();
            if (isHorizontal()) {
                thumbRect.y = trackRect.y + (trackRect.height - thumbRect.height) / 2;
            } else {
                thumbRect.x = trackRect.x + (trackRect.width - thumbRect.width) / 2;
            }
        }

        @Override
        protected Dimension getThumbSize() {
            return THUMB_SIZE;
        }

        private boolean isHorizontal() {
            return slider.getOrientation() == JSlider.HORIZONTAL;
        }

        @Override
        public void paint(final Graphics g, final JComponent c) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            super.paint(g, c);
        }

        @Override
        public void paintTrack(final Graphics g) {

            Graphics2D g2 = (Graphics2D) g;
            Shape clip = g2.getClip();

            boolean horizontal = isHorizontal();
            boolean inverted = slider.getInverted();

            // Color sombra
            g2.setColor(new Color(170, 170, 170));
            g2.fill(trackShape);

            // Color del fondo
            g2.setColor(new java.awt.Color(234, 234, 234));
            g2.setClip(trackShape);
            trackShape.y += 1;
            g2.fill(trackShape);
            trackShape.y = trackRect.y;

            g2.setClip(clip);

            //Se toma 50 como el valor central del slider
            if (slider.getValue() >= 50) {

                //Parte derecha
                if (horizontal) {
                    boolean ltr = slider.getComponentOrientation().isLeftToRight();
                    if (ltr) {
                        inverted = !inverted;
                    }
                    int thumbPos = thumbRect.x + thumbRect.width / 2;
                    if (inverted) {
                        //Pinta del color establecido la parte del slider de 50 a 100
                        g2.clipRect(190, 0, thumbPos - 190, slider.getHeight());
                    } else {
                        g2.clipRect(thumbPos, 0, slider.getWidth() - thumbPos, slider.getHeight());
                    }

                } else {
                    int thumbPos = thumbRect.y + thumbRect.height / 2;
                    if (inverted) {
                        g2.clipRect(0, 0, slider.getHeight(), thumbPos);
                    } else {
                        g2.clipRect(0, thumbPos, slider.getWidth(), slider.getHeight() - thumbPos);
                    }
                }
                g2.setColor(new Color(0, 204, 51));
                g2.fill(trackShape);
                g2.setClip(clip);
            } else {

                // Parte izquierda
                if (horizontal) {
                    boolean ltr = slider.getComponentOrientation().isLeftToRight();
                    if (ltr) {
                        inverted = !inverted;
                    }
                    int thumbPos = thumbRect.x + thumbRect.width / 2;
                    if (inverted) {
                        //Pinta del color establecido la parte del slider de 0 a 50
                        g2.clipRect(thumbPos, 0, 190 - thumbPos, slider.getHeight());
                    } else {
                        g2.clipRect(thumbPos, 0, slider.getWidth() - thumbPos, slider.getHeight());
                    }

                } else {
                    int thumbPos = thumbRect.y + thumbRect.height / 2;
                    if (inverted) {
                        g2.clipRect(0, 0, slider.getHeight(), thumbPos);
                    } else {
                        g2.clipRect(0, thumbPos, slider.getWidth(), slider.getHeight() - thumbPos);
                    }
                }
                g2.setColor(Color.RED);
                g2.fill(trackShape);
                g2.setClip(clip);
            }
        }

        @Override
        public void paintThumb(final Graphics g) {
            g.setColor(Color.GRAY);
            g.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
        }

        @Override
        public void paintFocus(final Graphics g) {
        }
    }
