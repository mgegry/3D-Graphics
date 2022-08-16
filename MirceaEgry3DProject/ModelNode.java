/**
 * This class holds model information about a node
 * This class was created fully with the help of online tutorials
 * University year: 2021-2022
 * @version 1.0
 */

import com.jogamp.opengl.*;

public class ModelNode extends SGNode {
    protected Model model;
    
    public ModelNode(String name, Model m) {
        super(name);
        model = m;
    }

    public void draw(GL3 gl) {
        model.render(gl, worldTransform);

        for (int i = 0; i < children.size(); i++) {
            children.get(i).draw(gl);
        }
    }
}