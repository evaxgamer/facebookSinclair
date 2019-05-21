package facebook;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class GraphViewer extends JFrame {
	private static final long serialVersionUID = 1L;


	/*
	 * Graph Viewer
	 *     for each level of depth up to 2 displays a users friend
	 *     Example
	 *       depth 0 - displays just user
	 *       depth 1 - displays user going to users friends
	 *       depth 2 - displays user going to friend then going to their friends
	 */
	public GraphViewer(Facebook facebook, FacebookUser user, int depth) throws CloneNotSupportedException {
		Graph<FacebookUser, String> g = new DirectedSparseMultigraph<>();
		g.addVertex(user);	
		if(depth > 0)
			for (FacebookUser friend: user.getFriends()) {
				FacebookUser friendCopy = new FacebookUser(friend.getUsername(), null, null);
				g.addVertex(friendCopy);
				g.addEdge(user.toString() + "-" + friendCopy.toString(), user, friendCopy);
				if(depth > 1)
					for(FacebookUser friendFriend: facebook.getUserClone(friend).getFriends()) {
						FacebookUser friendFriendCopy = new FacebookUser(friendFriend.getUsername() + " ", null, null);
						g.addVertex(friendFriendCopy);
						g.addEdge(friendCopy.toString() + "-" + friendFriendCopy.toString(), friendCopy, friendFriendCopy);
					}		
			}

		Layout<FacebookUser, String> layout = new ISOMLayout<>(g);
		layout.setSize(new Dimension(800,675));

		BasicVisualizationServer<FacebookUser,String> vv = 
				new BasicVisualizationServer<>(layout);
		vv.setPreferredSize(new Dimension(800,700));
		
		vv.getRenderContext().setVertexLabelTransformer(
				new ToStringLabeller<FacebookUser>());
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
		
	    Transformer<FacebookUser,Shape> vertexSize = new Transformer<FacebookUser,Shape>(){
            public Shape transform(FacebookUser user) {
            	Graphics graphics = getGraphics();
            	FontMetrics fontMetrics = graphics.getFontMetrics();
            	int radius = (int) (fontMetrics.stringWidth(user.toString()) * 1.1);
                return new Ellipse2D.Double(-15, -15, radius, radius);
            }
        };
        
        Transformer<FacebookUser, Paint> vertexPaint = new Transformer<FacebookUser, Paint>() {
			@Override
			public Paint transform(FacebookUser userToPaint) {
				if(userToPaint == user)
					return Color.RED;
				else
					return Color.BLUE;
			}
        	
        };
        
        vv.getRenderContext().setVertexShapeTransformer(vertexSize);
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);

		setTitle(user.toString() + "'s Neighborhood");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);     
		getContentPane().add(vv);
		pack();
		setVisible(true);
	}

	
}
