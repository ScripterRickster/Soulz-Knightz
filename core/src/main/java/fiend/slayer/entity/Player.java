package fiend.slayer.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import fiend.slayer.screens.GameScreen;
import fiend.slayer.weapons.HeldWeapon;

public class Player extends Entity {

    float speed = 8f;

    public float maxHP = 100;
    public float maxArmor = 10;
    public float maxEnergy = 10;

    public float hp = maxHP;
    public float armor = maxArmor;
    public float energy = maxEnergy;

    public boolean dead = false;

    Task regenArmor;

    public HeldWeapon held_weapon;

    public Player(final GameScreen gs) {
        super(gs,"player");

        sprite = new Sprite(new Texture("player.png"));
        autoSpriteSize();

        x = 0; y = 0;

        regenArmor = new Task(){
            @Override
            public void run(){
                if(!dead && armor < maxArmor){
                    armor++;
                    System.out.println("Armor Increased");
                }
            }
        };
        Timer.schedule(regenArmor,0f,15f);

        held_weapon = new HeldWeapon(gs, this);
    }

    @Override
    public void update(float delta) {
        if (dead) {
            regenArmor.cancel();
            return;
        }

        float prevX = x;
        float prevY = y;

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += speed * delta;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= speed * delta;
        }

        if (gs.mapCollisionCheck(this)) {
            x = prevX;
            y = prevY;
        }

        prevX = x;
        prevY = y;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= speed  * delta;
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            held_weapon.fire();
        }

        if (gs.mapCollisionCheck(this)) {
            x = prevX;
            y = prevY;
        }

    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.setPosition(x, y);
        sprite.draw(batch);
        held_weapon.render(batch);
    }

    public void damage(float dmg) {
        if (armor > 0) {
            armor = Math.max(0, armor - dmg);
        } else {
            hp = Math.max(0, hp - dmg);
        }

        if (hp <= 0) {
            dead = true;
        }
    }

    @Override
    public String toString(){
        return "Player Info | CORDS -> X: " + x + " , Y: " + y + " ; Stats -> HP: " + hp + " -> Armor: " + armor + " -> Energy Remaining: " + energy;
    }

}
