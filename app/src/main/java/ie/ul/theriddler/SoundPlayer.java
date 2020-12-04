package ie.ul.theriddler;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {

    private static SoundPool sSoundPool;
    private static int sClickSound;

    public SoundPlayer(Context context)
    {
        sSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);

        sClickSound = sSoundPool.load(context, R.raw.click, 1);
    }

    public void PlayClickSound()
    {
        sSoundPool.play(sClickSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

}
