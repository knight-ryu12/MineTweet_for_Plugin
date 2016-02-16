package com.ittekikun.plugin.MineTweet.Twitter.Eew;

import com.ittekikun.plugin.MineTweet.MineTweet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class NoticeBuilder
{
    public static void noticeEew(String[] eew)
    {
        String[] eewMes = new String[9];

        Bukkit.broadcastMessage("テスト");

        eewMes[0] = ChatColor.RED +    "----------緊急地震速報----------";
        eewMes[1] = ChatColor.YELLOW + "発表時刻: " + eew[3];
        eewMes[2] = (ChatColor.YELLOW + "震源地: " + eew[9]);
        eewMes[3] = ChatColor.YELLOW + "マグニチュード: " + eew[11];
        eewMes[4] = ChatColor.YELLOW + "深さ: " + eew[10];
        eewMes[5] = ChatColor.YELLOW + "最大震度: " + eew[12];
        eewMes[6] = ChatColor.RED +    "震源地付近にお住まいの方は大きな地震に注意してください。";
        eewMes[7] = ChatColor.RED +    "テレビ・ラジオ等で正確な情報を収集してください。";
        eewMes[8] = ChatColor.RED +    "※この情報は震度速報ではありません。あくまでも、地震の規模を早期に推定するものです。";
        eewMes[9] = ChatColor.RED +    "--------------------------------";

        Bukkit.broadcastMessage( eewMes[2]);

        broadcastMessage(eewMes);
    }

    public static void broadcastMessage(String[] eewMes)
    {
        for(String message : eewMes)
        {
            Bukkit.broadcastMessage(message);
        }
    }
}
