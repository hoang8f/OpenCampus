package info.hoang8f;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;

import com.colorcloud.wifichat.R;

/**
 * Created by hoang8f on 8/1/14.
 */
public class SoundManager {
    private static SoundManager ourInstance = new SoundManager();

    private int duration = 800;
    Handler handler;
    MediaPlayer mPlayerDo;
    MediaPlayer mPlayerRe;
    MediaPlayer mPlayerMi;
    MediaPlayer mPlayerFa;
    MediaPlayer mPlayerSon;
    MediaPlayer mPlayerLa;
    MediaPlayer mPlayerSi;

    public static SoundManager getInstance() {
        return ourInstance;
    }

    private SoundManager() {
        handler = new Handler();
    }

    public void playDo(Context context, int delayTime) {
        if (mPlayerDo == null) {
            mPlayerDo = MediaPlayer.create(context, R.raw.c5);
            mPlayerDo.setLooping(true);
        }
        play(context, mPlayerDo, delayTime);
    }

    public void playRe(Context context, int delayTime) {
        if (mPlayerRe == null) {
            mPlayerRe = MediaPlayer.create(context, R.raw.d5);
            mPlayerRe.setLooping(true);
        }
        play(context, mPlayerRe, delayTime);
    }

    public void playMi(Context context, int delayTime) {
        if (mPlayerMi == null) {
            mPlayerMi = MediaPlayer.create(context, R.raw.e5);
            mPlayerMi.setLooping(true);
        }
        play(context, mPlayerMi, delayTime);
    }

    public void playFa(Context context, int delayTime) {
        if (mPlayerFa == null) {
            mPlayerFa = MediaPlayer.create(context, R.raw.f5);
            mPlayerFa.setLooping(true);
        }
        play(context, mPlayerFa, delayTime);
    }

    public void playSon(Context context, int delayTime) {
        if (mPlayerSon == null) {
            mPlayerSon = MediaPlayer.create(context, R.raw.g4);
            mPlayerSon.setLooping(true);
        }
        play(context, mPlayerSon, delayTime);
    }

    public void playLa(Context context, int delayTime) {
        if (mPlayerLa == null) {
            mPlayerLa = MediaPlayer.create(context, R.raw.a4);
            mPlayerLa.setLooping(true);
        }
        play(context, mPlayerLa, delayTime);
    }

    public void playSi(Context context, int delayTime) {
        if (mPlayerSi == null) {
            mPlayerSi = MediaPlayer.create(context, R.raw.b4);
            mPlayerSi.setLooping(true);
        }
        play(context, mPlayerSi, delayTime);
    }

    private void play(Context context, final MediaPlayer mPlayer, int delayTime) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPlayer.start();
            }
        }, delayTime);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mPlayer != null) {
                    mPlayer.pause();
                }
            }
        }, duration + delayTime);
    }

}
