package com.ittekikun.plugin.MineTweet.Twitter.Stream.Eew;

import com.ittekikun.plugin.MineTweet.MineTweet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class NoticeBuilder
{
    public static void noticeEewMessage(String[] eew, boolean demo)
    {
        List<String> eewMes = new ArrayList<String>();

        if(!demo)
        {
            eewMes.add(ChatColor.RED +    "----------緊急地震速報----------");
        }
        else
        {
            eewMes.add(ChatColor.RED +    "----------緊急地震速報(動作確認モード有効中)----------");
            eewMes.add(ChatColor.RED +    "テレビなどで普段発表されていない緊急地震速報を表示している可能性があります。");
            eewMes.add(ChatColor.RED +    "念の為、テレビ・ラジオ等で正確な情報を収集してください。");
            eewMes.add(ChatColor.RED +    "動作確認し次第、動作確認モードを無効にして下さい。");
        }

        eewMes.add(ChatColor.YELLOW + "発表時刻: " + ChatColor.WHITE + eew[2]);
        eewMes.add(ChatColor.YELLOW + "震源地: " + ChatColor.WHITE + eew[9]);
        eewMes.add(ChatColor.YELLOW + "マグニチュード: " + ChatColor.WHITE + eew[11]);
        eewMes.add(ChatColor.YELLOW + "深さ: " + ChatColor.WHITE + eew[10]);
        eewMes.add(ChatColor.YELLOW + "最大震度: " + ChatColor.WHITE + eew[12]);
        eewMes.add(ChatColor.RED +    "震源地付近にお住まいの方は大きな地震に注意してください。");
        eewMes.add(ChatColor.RED +    "この情報を鵜呑みにせず、テレビ・ラジオ等で正確な情報を収集してください。");
        eewMes.add(ChatColor.RED +    "※この情報は震度速報ではありません。あくまでも、地震の規模を早期に推定するものです。");
        eewMes.add(ChatColor.RED +    "--------------------------------");

        broadcastMessage(eewMes);
    }

    public static void broadcastMessage(List eewMes)
    {

        for(int i = 0; i < eewMes.size(); ++i)
        {
            Bukkit.broadcastMessage(eewMes.get(i).toString());
        }
    }
}
