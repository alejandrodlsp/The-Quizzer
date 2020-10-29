package ie.ul.theriddler;

import android.util.Base64;

public class Util {
    /**
     * Decodes a BASE 64 encoded string
     * @param string String to be decoded from Base64 encoding
     * @return decoded string
     */
    public static String DecodeBase64(String string)
    {
        try {
            byte[] tmp = Base64.decode(string, Base64.DEFAULT);
            return new String(tmp);
        } catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }
}
