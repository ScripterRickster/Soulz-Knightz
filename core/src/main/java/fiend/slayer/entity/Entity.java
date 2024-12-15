package fiend.slayer.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import fiend.slayer.screens.GameScreen;

public abstract class Entity {

    protected final GameScreen gs;
    protected Sprite sprite;

    public boolean dead = false;
    public float x, y;
    public String type;

    public Entity(final GameScreen gs,String type) {
        this.gs = gs;
        this.type = type;

        // subclasses are responsible for initializing x, y, and sprite
    }

    public Rectangle getRectangle(){
        return new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    public void update(float delta) {
    }

    public boolean collisionCheck(Entity e){
        return this.getRectangle().overlaps(e.getRectangle());
    }

    public float getHeading(Entity e){
        return (float) (MathUtils.atan2(e.y-y, e.x-x));
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Vector2 center() { return new Vector2(x - sprite.getWidth()/2, y - sprite.getHeight()/2); }

}
