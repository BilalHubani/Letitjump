package com.example.billy.jumpit.controller.activities.gameViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.billy.jumpit.R;
import com.example.billy.jumpit.controller.activities.Scenes.HistoryScene;
import com.example.billy.jumpit.controller.activities.main.MainActivity;
import com.example.billy.jumpit.controller.managers.UserCallback;
import com.example.billy.jumpit.controller.managers.UserManager;
import com.example.billy.jumpit.model.BitmapSet;
import com.example.billy.jumpit.model.Bonk;
import com.example.billy.jumpit.model.Character;
import com.example.billy.jumpit.model.DragonBitmapSet;
import com.example.billy.jumpit.model.DragonSkin;
import com.example.billy.jumpit.model.PokemonBitmapSet;
import com.example.billy.jumpit.model.RositoBitmapSet;
import com.example.billy.jumpit.model.RositoSkin;
import com.example.billy.jumpit.model.TerrenosBitmapSet;
import com.example.billy.jumpit.model.UserCustomAtributes;

import java.util.ArrayList;
import java.util.List;

import static android.R.drawable.ic_media_pause;
import static android.R.drawable.ic_media_play;

/**
 * Created by dam on 2/3/17.
 */

public class GameViewHistoria extends View implements UserCallback{
    private Paint paint;
    private BitmapSet bitmapSet;
    private PokemonBitmapSet pokemonBitmapSet;
    private RositoBitmapSet rositoBitmapSet;
    private TerrenosBitmapSet terrenosBitmapSet;

    public HistoryScene getScene() {
        return scene;
    }

    public void setScene(HistoryScene scene) {
        this.scene = scene;
    }

    private HistoryScene scene;
    private Bonk bonk;
    private RositoSkin rositoSkin;
    private Character character;
    private ImageButton pauseButton;
    private int vel = 4;
    private int velCounter = 1;
    private boolean paused = false;
    private boolean jump = false;
    private boolean stateJumping = false;
    private int jumpCounter = 0;
    private int jumpIncrement = 2;
    private int jumpMaxHeigh = 120;
    private int jumpAux;
    private int score = 0;
    private MainActivity mainActivity;
    private ImageButton goHome;
    private ImageButton reload;
    private List<BitmapSet> bitmapSetList;
    private List<Character> characterList;
    private int characterIndex = 0;
    private int bitmapIndex = 3;
    private UserCustomAtributes user;


    public int getNivel() {
        return nivel;
    }

    int nivel = R.raw.nivel2;

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    private TextView scoreTextView;


    public GameViewHistoria(Context context) {
        this(context, null, 0);
    }
    public GameViewHistoria(Context context,int nivel) {
        this(context, null, 0);
        this.nivel = nivel;
    }

    public GameViewHistoria(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public GameViewHistoria(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // Arraylist de bitmaps
        bitmapSetList = new ArrayList<>();
// Declaramos y añadimos bitmaps al array
        bitmapSet = new BitmapSet(this.getResources());
        terrenosBitmapSet = new TerrenosBitmapSet(this.getResources());
        scene = new HistoryScene(this);
        this.nivel = nivel;
        pokemonBitmapSet = new PokemonBitmapSet(this.getResources());
        rositoBitmapSet = new RositoBitmapSet(this.getResources());
        bitmapSetList.add(bitmapSet);
        bitmapSetList.add(pokemonBitmapSet);
        bitmapSetList.add(rositoBitmapSet);
        bitmapSetList.add(terrenosBitmapSet);
        bonk = new Bonk(bitmapSetList.get(bitmapIndex));
        scene.load(nivel);
//        character = new Character(pokemonBitmapSet);
        rositoSkin = new RositoSkin(bitmapSetList.get(2));
        paint = new Paint();
        paint.setTextSize(10);
    }

    // dibujar la pantalla
    @Override
    public void onDraw(Canvas canvas) {
        this.postInvalidateDelayed(10);
        if (!paused) {
            if (bitmapSet == null) return;
            if (rositoSkin == null) return;
            scene.setPausa(false);
            float sc = getHeight() / (16 * 16f);
            canvas.scale(sc, sc);
            if (jump) {
                doGoingUp();
            } else if (!checkGround()) {
                doGoingDown();
            }
            scene.draw(canvas, vel);
//            bonk.draw(canvas);
//            character.draw(canvas);
            rositoSkin.draw(canvas);
            canvas.drawText("SCORE: "+score, 35,20, paint);
            score++;
        }else {
            if (bitmapSet == null) return;
            float sc = getHeight() / (16 * 16f);
            canvas.scale(sc, sc);
            scene.draw(canvas, 0);
            scene.setPausa(true);
//            bonk.draw(canvas);
//            character.draw(canvas);
            rositoSkin.draw(canvas);
            canvas.drawText("SCORE: "+score, 200,100, paint);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        pauseButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (paused){
                    vel = 4;
                    paused = false;
                    pauseButton.setBackgroundResource(R.drawable.botonespausa);
                }else{
                    vel = 0;
                    paused = true;
                    pauseButton.setBackgroundResource(R.drawable.botonesplay);
                }
            }
        });
        if (event.getAction() == MotionEvent.ACTION_DOWN && !stateJumping){
//            if (character.getFrame() <= 2 ){
//                character.setFrameCounter(0);
//                character.setFrame(3);
//            }
            if (rositoSkin.getFrame() <= 5 ){
                rositoSkin.setFrameCounter(0);
                rositoSkin.setFrame(6);
            }
            jump = true;
            stateJumping = true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP){
            jump = false;
        }
        return true;
    }
    public void doGoingUp(){

        if (jumpCounter<jumpMaxHeigh-10){
//            bonk.setY(bonk.getY()-jumpIncrement);
//            character.setY(character.getY()-jumpIncrement);
            rositoSkin.setY(rositoSkin.getY()-jumpIncrement);
            jumpCounter += jumpIncrement;
            jumpAux = jumpCounter;
        }else if(jumpCounter<jumpMaxHeigh){
//            bonk.setY(bonk.getY() - jumpIncrement+1);
//            character.setY(character.getY() - jumpIncrement+1);
            rositoSkin.setY(rositoSkin.getY() - jumpIncrement+1);
            jumpCounter += jumpIncrement-1;
            jumpAux = jumpCounter;
        }else if (jumpCounter >= jumpMaxHeigh){
            jump = false;
        }
    }
    public void doGoingDown(){
//        if (character.getFrame() <= 2 ){
//            character.setFrameCounter(0);
//            character.setFrame(3);
//        }
        if (rositoSkin.getFrame() <= 5 ){
            rositoSkin.setFrameCounter(0);
            rositoSkin.setFrame(6);
        }
        if (jumpCounter>jumpAux-10){
//            bonk.setY(bonk.getY() + jumpIncrement-1);
//            character.setY(character.getY() + jumpIncrement-1);
            rositoSkin.setY(rositoSkin.getY() + jumpIncrement-1);
            jumpCounter -= jumpIncrement-1;
        }else {
//            bonk.setY(bonk.getY()+jumpIncrement);
//            character.setY(character.getY()+jumpIncrement);
            rositoSkin.setY(rositoSkin.getY()+jumpIncrement);
            jumpCounter -= jumpIncrement;
        }
    }
    public boolean checkGround() {
//        if (character.getFrame() > 2 && !stateJumping){
//            character.setFrameCounter(0);
//            character.setFrame(0);
//        }
        if (rositoSkin.getFrame() > 5 && !stateJumping){
            rositoSkin.setFrameCounter(0);
            rositoSkin.setFrame(0);
        }
//        int r = bonk.getY() >> 4;
//        int r = (character.getY()) >> 4;
        int r = (rositoSkin.getY()) >> 4;
        if (r >=16){
            paused = true;
            end();
        }
//        int c = bonk.getX() >> 4;
//        int c = character.getX() >> 4;
        int c = rositoSkin.getX() >> 4;
        if (!scene.isGround(r+2, c)){
            stateJumping = true;
            return false;
        }else {
            if (!jump) {
//                bonk.setY(r << 4);
//                character.setY((r << 4) + 8);
                rositoSkin.setY((r << 4) + 8);
                stateJumping = false;
                jumpCounter = 0;
            }
        }
        if(scene.isWall(r, c+20)){
            UserManager.getInstance().getUserCustomAtributes(this);
            paused = true;
            end();
        }
        return true;
    }

    public void end(){
        goHome.setVisibility(VISIBLE);
        reload.setVisibility(VISIBLE);

        goHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.onCreate(mainActivity.getBundle());
            }
        });
        reload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                scene = new HistoryScene();
                bonk = new Bonk(bitmapSet);
//                character = new Character(pokemonBitmapSet);
                rositoSkin = new RositoSkin(rositoBitmapSet);
                vel = 4;
                velCounter = 1;
                paused = false;
                jump = false;
                stateJumping = false;
                jumpCounter = 0;
                jumpIncrement = 2;
                score = 0;
                goHome.setVisibility(INVISIBLE);
                reload.setVisibility(INVISIBLE);
                nivel = getScene().getNivel();
                scene.load(nivel);
            }
        });
    }

    public TextView getScoreTextView() {
        return scoreTextView;
    }

    public void setScoreTextView(TextView scoreTextView) {
        this.scoreTextView = scoreTextView;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public ImageButton getPauseButton() {
        return pauseButton;
    }

    public void setPauseButton(ImageButton pauseButton) {
        this.pauseButton = pauseButton;
    }

    public ImageButton getGoHome() {
        return goHome;
    }

    public void setGoHome(ImageButton goHome) {
        this.goHome = goHome;
    }

    public ImageButton getReload() {
        return reload;
    }

    public void setReload(ImageButton reload) {
        this.reload = reload;
    }

    public BitmapSet getBitmapSet() {
        return bitmapSet;
    }

    public void setBitmapSet(BitmapSet bitmapSet) {
        this.bitmapSet = bitmapSet;
    }

    public TerrenosBitmapSet getTerrenosBitmapSet() {
        return terrenosBitmapSet;
    }


    @Override
    public void onSucces() {

    }

    @Override
    public void onSucces(UserCustomAtributes user) {
        this.user = user;
        Log.e("----->>>>" , "HAS TERMINADO");
        int sumar = 50;
        this.user.setMoneyGame(this.user.getMoneyGame() + sumar);
        Toast.makeText(getContext(),"OBTIENES " + sumar + " MONEDAS", Toast.LENGTH_SHORT).show();
        UserManager.getInstance().updateUser(this, this.user);
    }

    @Override
    public void onFailure(Throwable t) {
        Log.e("--->>", "NÑEEEEEEEEEEEEEEEEE");
    }
}
