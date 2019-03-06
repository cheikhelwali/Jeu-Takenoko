package takenoko_pack.visualiser;

import takenokopack.controller.Enums;
import takenokopack.models.Bamboo;
import takenokopack.models.Bot;
import takenokopack.models.HexCoordSystem;
import takenokopack.models.Hexagon;
import takenokopack.models.objectives.Objective;
import takenokopack.models.personages.Personage;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

public class DrawPlots extends JFrame{
    private DrawingPanel drawingPanel;
    private JPanel mainPanel ;
    private final int scale = 100;
    JScrollPane scrPane;
    public DrawPlots() {

        super("Lines Drawing Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new JPanel();
        mainPanel.setLayout( new BoxLayout( mainPanel, BoxLayout.Y_AXIS ) );
        scrPane = new JScrollPane(mainPanel);
        // only a configuration to the jScrollPane...
        scrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrPane.getVerticalScrollBar().setUnitIncrement(20);
        add(scrPane);
        setLocationByPlatform(true);
        setVisible(true);
        pack();
    }

    public void draw(Collection<Hexagon> hexagons, Personage panda, Personage gardener, Bot player,List<Enums.Action> actions,int turn){
        List<Hex> hexList = new ArrayList<>();
        for (Hexagon hex:hexagons)
            hexList.add(new Hex(new Point(hex.getX()*scale+500, hex.getY()*scale+500) ,60,hex.getZ()*scale,hex));
        drawingPanel = new DrawingPanel(hexList,panda, gardener,player,actions,turn);
        // Adding the component to the JScrollPane
        JPanel pnl = (JPanel) scrPane.getViewport( ).getView( );
        pnl.add( drawingPanel );
        //mainPanel.add(drawingPanel);
        pack();

    }

    public class DrawingPanel extends JPanel {
        private static final long serialVersionUID = 5701311351092275287L;

        private List<Hex> hexList;
        private Hexagon pandaHex;
        private Hexagon gardenerHex;
        private String namePlayer;
        private String ownBamboo;
        private String actionList;
        private String objectivesToDo;
        private String goablePanda;
        private String goableGardener;
        private int nbOfSections;
        private int turn;

        public DrawingPanel(List<Hex> hexList,Personage panda, Personage gardener,Bot player,List<Enums.Action> actions,int turn) {
            this.hexList = new ArrayList<>(hexList);
            this.pandaHex = panda.getCurrentHex();
            this.gardenerHex = gardener.getCurrentHex();
            this.ownBamboo = player.getOwnBamboos().toString();
            this.namePlayer = player.getName();
            this.objectivesToDo = player.getObjectivesToDo().toString();
            this.actionList = actions.toString();
            this.goablePanda = HexCoordSystem.INSTANCE.possibleSlots(pandaHex).toString();
            this.goableGardener = HexCoordSystem.INSTANCE.possibleSlots(gardenerHex).toString();
            this.turn = turn;
            this.setPreferredSize(new Dimension(1000, 1000));

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawString("Turn:"+turn,20,20);
            g.drawString("Player:"+namePlayer,20,40);
            g.drawString("Actions:"+actionList,20,80);
            g.drawString("OwnBamboos:"+ownBamboo,20,100);
            g.drawString("ObjToDo:"+objectivesToDo,20,120);
            g.drawString("Goable Panda:"+goablePanda,20,150);
            g.drawString("Goable Gardener:"+goableGardener,20,170);

            for(Hex hexagon:hexList){
                if(hexagon.realHex.getY() == 0 && hexagon.realHex.getX()==0){
                    g.setColor(Color.CYAN);
                    g.fillPolygon(hexagon.polygon);
                }
                else{
                    try {
                        g.setColor((Color) Color.class.getField(hexagon.realHex.getColor().toString()).get(null));
                        g.fillPolygon(hexagon.polygon);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                g.setColor(Color.BLUE);
                g.drawString(hexagon.realHex.getImprovement().toString(),150+hexagon.center.x-(hexagon.center.x + hexagon.z)/2,+20+hexagon.center.y);
                g.drawString(Integer.toString(hexagon.nbBamboos),180+hexagon.center.x-(hexagon.center.x + hexagon.z)/2,+40+hexagon.center.y);

                g.drawString(Integer.toString(hexagon.realHex.getX())+ " "
                        + Integer.toString(hexagon.realHex.getY())+" "
                        + Integer.toString(hexagon.realHex.getZ()), 200+hexagon.center.x-(hexagon.center.x + hexagon.z)/2,hexagon.center.y);

                if(hexagon.realHex.equals(gardenerHex))
                    g.drawString("G",200+hexagon.center.x-(hexagon.center.x + hexagon.z)/2,-20+hexagon.center.y);
                if(hexagon.realHex.equals(pandaHex))
                    g.drawString("P",180+hexagon.center.x-(hexagon.center.x + hexagon.z)/2,-20+hexagon.center.y);
                g.drawPolygon(hexagon.polygon);
            }
        }
    }

    public class Hex {
        private final int radius;

        private final Point center;

        private final Polygon polygon;

        private Hexagon realHex;
        private int z;
        private int nbBamboos;

        public Hex(Point center, int radius,int z, Hexagon hex) {
            this.center= center;
            this.radius = radius;
            this.z = z;
            this.realHex = hex;
            this.nbBamboos = hex.getBamboo().getNbSections();
            this.polygon = createHex();
        }

        private Polygon createHex() {
            Polygon polygon = new Polygon();

            for (int i = 0; i < 6; i++) {
                int xval = (int) (200+center.x/2-z/2+ radius
                        * Math.sin(i * 2 * Math.PI / 6D));
                int yval = (int) ( center.y + radius
                        * Math.cos(i * 2 * Math.PI / 6D));
                polygon.addPoint(xval, yval);
            }
            return polygon;
        }

        public int getRadius() {
            return radius;
        }

        public Point getCenter() {
            return center;
        }


    }
}
