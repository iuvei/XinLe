package com.xinle.lottery.game;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xinle.lottery.R;
import com.xinle.lottery.data.Lottery;
import com.xinle.lottery.data.Method;
import com.xinle.lottery.pattern.PickNumber;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by ACE-PC on 2016/2/18.
 */
public class TextGame extends Game {
    private static String[] dxdsText = new String[]{"大", "小", "单", "双"};
    private static HashMap<String, String> dxdsMap = new HashMap<String, String>() {{
        put("大", "1");
        put("小", "0");
        put("单", "3");
        put("双", "2");
    }};
    private static String[] specialText = new String[]{"豹子", "顺子", "对子"};
    private static HashMap<String, String> specialMap = new HashMap<String, String>() {{
        put("豹子", "0");
        put("顺子", "1");
        put("对子", "2");
    }};
    private static String[] funText = new String[]{"龙", "虎", "和"};
    private static HashMap<String, String> funMap = new HashMap<String, String>() {{
        put("龙", "2");
        put("虎", "0");
        put("和", "1");
    }};
    private static String[] dragonTigerText = new String[]{"龙", "虎"};
    private static HashMap<String, String> dragonTigerTextMap = new HashMap<String, String>() {{
        put("龙", "1");
        put("虎", "0");
    }};

    public TextGame(Activity activity, Method method, Lottery lottery) {
        super(activity, method, lottery);
    }

    @Override
    public void onInflate() {
        try {
            java.lang.reflect.Method function = getClass().getMethod(method.getNameEn() + method.getId(), Game.class);
            function.invoke(null, this);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("SscTextGame", "onInflate: " + "//" + method.getNameCn() + " " + method.getNameEn() + method.getId
                    () + " public static void " + method.getNameEn() + method.getId() + "(Game game) {}");
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }

    public String getWebViewCode() {
        JsonArray jsonArray = new JsonArray();
        for (PickNumber pickNumber : pickNumbers) {
            jsonArray.add(transform(pickNumber.getCheckedNumber(), pickNumber.getNumberCount(), false));
        }
        return jsonArray.toString();
    }

    public String getSubmitCodes() {
        String[] text;
        StringBuilder builder = new StringBuilder();
        switch (method.getNameEn()) {
            case "teshuhaoma":
                text = specialText;
                break;
            case "baozi":
                text = new String[]{"豹子"};
                break;
            case "shunzi":
                text = new String[]{"顺子", "对子"};
                break;
            case "duizi":
                text = new String[]{"对子"};
                break;
            case "houerdaxiaodanshuang":
            case "housandaxiaodanshuang":
            case "qianerdaxiaodanshuang":
            case "qiansandaxiaodanshuang":
                for (int i = 0, size = pickNumbers.size(); i < size; i++) {
                    builder.append(transformTextMap(pickNumbers.get(i).getCheckedNumber(), dxdsText, dxdsMap, true,false));
                    if (i != size - 1) {
                        builder.append("|");
                    }
                }
                break;
            case "wuxinghezhidaxiaodanshuang":
            case "dxds":
                for (int i = 0, size = pickNumbers.size(); i < size; i++) {
                    builder.append(transformTextMap(pickNumbers.get(i).getCheckedNumber(), dxdsText, dxdsMap, true,false));
                }
                break;
            case "longhu":
                for (int i = 0, size = pickNumbers.size(); i < size; i++) {
                    builder.append(transformTextMap(pickNumbers.get(i).getCheckedNumber(), dragonTigerText, dragonTigerTextMap, true, false));
                    if (i != size - 1) {
                        builder.append("|");
                    }
                }
                break;
            case "wq":
            case "wb":
            case "ws":
            case "wg":
            case "qb":
            case "qs":
            case "qg":
            case "bs":
            case "bg":
            case "sg":
                for (int i = 0, size = pickNumbers.size(); i < size; i++) {
                    builder.append(transformTextMap(pickNumbers.get(i).getCheckedNumber(), funText, funMap, true, false));
                }
                break;
            default:
                text = new String[]{""};
                break;
        }

        return builder.toString();
    }

    public void onRandomCodes() {
        try {
            java.lang.reflect.Method function = getClass().getMethod(method.getNameEn() + method.getId() + "Random",
                    Game.class);
            function.invoke(null, this);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("SscCommonGame", "onInflate: " + "//" + method.getNameCn() + "随机 " + method.getNameEn() + " public " +
                    "static void " + method.getNameEn() + method.getId() + "Random" + "(Game game) {}");
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }

    public static View createDefaultPickLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column, null, false);
    }

    private static void addPickTextGame(Game game, View topView, String title, String[] disText, boolean flag) {
        PickNumber pickNumberText = new PickNumber(topView, title);
        pickNumberText.getNumberGroupView()
                .setDisplayText(disText)
                .setNumber(1, disText.length)
                .setChooseMode(flag);
        pickNumberText.setControlBarHide(true);
        game.addPickNumber(pickNumberText);
    }

    private static void createPicklayout(Game game, String[] name, String[] disText, boolean flag) {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = createDefaultPickLayout(game.getTopLayout());
            addPickTextGame(game, view, name[i], disText, flag);
            views[i] = view;
        }

        ViewGroup topLayout = game.getTopLayout();
        for (View view : views) {
            topLayout.addView(view);
        }
    }

    //后二大小单双 houerdaxiaodanshuang58
    public static void houerdaxiaodanshuang58(Game game) {
        createPicklayout(game, new String[]{"十位", "个位"}, dxdsText, false);
    }

    //后三大小单双 housandaxiaodanshuang53
    public static void housandaxiaodanshuang53(Game game) {
        createPicklayout(game, new String[]{"百位", "十位", "个位"}, dxdsText, false);
    }

    //前二大小单双 qianerdaxiaodanshuang19
    public static void qianerdaxiaodanshuang19(Game game) {
        createPicklayout(game, new String[]{"万位", "千位"}, dxdsText, false);
    }

    //前三大小单双 qiansandaxiaodanshuang22
    public static void qiansandaxiaodanshuang22(Game game) {
        createPicklayout(game, new String[]{"万位", "千位", "百位"}, dxdsText, false);
    }

    //五星和值大小单双 wuxinghezhidaxiaodanshuang495
     public static void wuxinghezhidaxiaodanshuang495(Game game) {
         createPicklayout(game, new String[]{"五星和值"}, dxdsText, false);
     }

    //特殊号码 teshuhaoma48
    public static void teshuhaoma48(Game game) {
        createPicklayout(game, new String[]{"特殊号码"}, specialText, false);
    }

    //豹子 baozi388
    public static void baozi388(Game game) {
        createPicklayout(game, new String[]{"豹子"}, new String[]{"豹子"}, false);
    }

    //顺子 shunzi387
    public static void shunzi387(Game game) {
        createPicklayout(game, new String[]{"顺子"}, new String[]{"顺子"}, false);
    }

    //对子 duizi385
    public static void duizi385(Game game) {
        createPicklayout(game, new String[]{"对子"}, new String[]{"对子"}, false);
    }

    //特殊号码 teshuhaoma155
    public static void teshuhaoma155(Game game) {
        createPicklayout(game, new String[]{"特殊号码"}, specialText, false);
    }

    //豹子 baozi393
    public static void baozi393(Game game) {
        createPicklayout(game, new String[]{"豹子"}, new String[]{"豹子"}, false);
    }

    //顺子 shunzi392
    public static void shunzi392(Game game) {
        createPicklayout(game, new String[]{"顺子"}, new String[]{"顺子"}, false);
    }

    //对子 duizi391
    public static void duizi391(Game game) {
        createPicklayout(game, new String[]{"对子"}, new String[]{"对子"}, false);
    }

    //特殊号码 teshuhaoma57
    public static void teshuhaoma57(Game game) {
        createPicklayout(game, new String[]{"特殊号码"}, specialText, false);
    }

    //豹子 baozi390
    public static void baozi390(Game game) {
        createPicklayout(game, new String[]{"豹子"}, new String[]{"豹子"}, false);
    }

    //顺子 shunzi389
    public static void shunzi389(Game game) {
        createPicklayout(game, new String[]{"顺子"}, new String[]{"顺子"}, false);
    }

    //对子 duizi386
    public static void duizi386(Game game) {
        createPicklayout(game, new String[]{"对子"}, new String[]{"对子"}, false);
    }

    //万千 wq352
    public static void wq352(Game game) {
        createPicklayout(game, new String[]{"万:千"}, funText, false);
    }

    //万百 wb353
    public static void wb353(Game game) {
        createPicklayout(game, new String[]{"万:百"}, funText, false);
    }

    //万十 ws354
    public static void ws354(Game game) {
        createPicklayout(game, new String[]{"万:十"}, funText, false);
    }

    //万个 wg355
    public static void wg355(Game game) {
        createPicklayout(game, new String[]{"万:个"}, funText, false);
    }

    //千百 qb356
    public static void qb356(Game game) {
        createPicklayout(game, new String[]{"千:百"}, funText, false);
    }

    //千十 qs357
    public static void qs357(Game game) {
        createPicklayout(game, new String[]{"千:十"}, funText, false);
    }

    //千个 qg358
    public static void qg358(Game game) {
        createPicklayout(game, new String[]{"千:个"}, funText, false);
    }

    //百十 bs359
    public static void bs359(Game game) {
        createPicklayout(game, new String[]{"百:十"}, funText, false);
    }

    //百个 bg360
    public static void bg360(Game game) {
        createPicklayout(game, new String[]{"百:个"}, funText, false);
    }

    //十个 sg361
    public static void sg361(Game game) {
        createPicklayout(game, new String[]{"十:个"}, funText, false);
    }

    //FC3D 虎龙和
    //百十 bs372
    public static void bs372(Game game) {
        createPicklayout(game, new String[]{"百:十"}, funText, false);
    }

    //百个 bg373
    public static void bg373(Game game) {
        createPicklayout(game, new String[]{"百:个"}, funText, false);
    }

    //十个 sg374
    public static void sg374(Game game) {
        createPicklayout(game, new String[]{"十:个"}, funText, false);
    }

    //北京PK10
    //大小单双 dxds176
    public static void dxds176(Game game) {
        createPicklayout(game, new String[]{"和值"}, dxdsText, false);
    }

    //龙虎 longhu178
    public static void longhu178(Game game) {
        createPicklayout(game, new String[]{"1V10", "2V9", "3V8", "4V7", "5V6"}, dragonTigerText, false);
    }

    /**
     * 大小单双
     **/
    //后二大小单双随机 houerdaxiaodanshuang public static void houerdaxiaodanshuangRandom(Game game) {}
    //后三大小单双随机 housandaxiaodanshuang public static void housandaxiaodanshuangRandom(Game game) {}
    //前二大小单双随机 qianerdaxiaodanshuang public static void qianerdaxiaodanshuangRandom(Game game) {}
    //前三大小单双随机 qiansandaxiaodanshuang public static void qiansandaxiaodanshuangRandom(Game game) {}

    //特殊号码随机 teshuhaoma
    public static void teshuhaomaRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, specialText.length, 1));
        game.notifyListener();
    }

    //豹子随机 baozi
    public static void baoziRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, 1, 1));
        game.notifyListener();
    }

    //顺子随机 shunzi
    public static void shunziRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, 1, 1));
        game.notifyListener();
    }

    //对子随机 duizi
    public static void duiziRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, 1, 1));
        game.notifyListener();
    }

    /**
     * 龙虎和
     **/
    //万千随机 wq
    public static void wq352Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, funText.length, 1));
        game.notifyListener();
    }

    //万百随机 wb
    public static void wb353Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, funText.length, 1));
        game.notifyListener();
    }

    //万十随机 ws
    public static void ws354Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, funText.length, 1));
        game.notifyListener();
    }

    //万个随机 wg
    public static void wg355Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, funText.length, 1));
        game.notifyListener();
    }

    //千百随机 qb
    public static void qb356Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, funText.length, 1));
        game.notifyListener();
    }

    //千十随机 qs
    public static void qs357Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, funText.length, 1));
        game.notifyListener();
    }

    //千个随机 qg
    public static void qg358Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, funText.length, 1));
        game.notifyListener();
    }

    //百十随机 bs
    public static void bs359Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, funText.length, 1));
        game.notifyListener();
    }

    //百个随机 bg
    public static void bg360Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, funText.length, 1));
        game.notifyListener();
    }

    //十个随机 sg
    public static void sg361Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, funText.length, 1));
        game.notifyListener();
    }

    //大小单双 dxds176
    public static void dxds176Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, dxdsText.length, 1));
        game.notifyListener();
    }

    //龙虎 longhu178
    public static void longhu178Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(new ArrayList<>());
        game.notifyListener();
        PickNumber pickNumber = game.pickNumbers.get(new Random().nextInt(game.pickNumbers.size()));
        pickNumber.onRandom(random(1, dragonTigerText.length, 1));
        game.notifyListener();
    }

    //后二大小单双随机 houerdaxiaodanshuang
    public static void houerdaxiaodanshuang58Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, dxdsText.length, 1));
        game.notifyListener();
    }

    //后三大小单双随机 housandaxiaodanshuang
     public static void housandaxiaodanshuang53Random(Game game) {
         for (PickNumber pickNumber : game.pickNumbers)
             pickNumber.onRandom(random(1, dxdsText.length, 1));
         game.notifyListener();
     }

    //前二大小单双随机 qianerdaxiaodanshuang
     public static void qianerdaxiaodanshuang19Random(Game game) {
         for (PickNumber pickNumber : game.pickNumbers)
             pickNumber.onRandom(random(1, dxdsText.length, 1));
         game.notifyListener();
     }

    //前三大小单双随机 qiansandaxiaodanshuang
     public static void qiansandaxiaodanshuang22Random(Game game) {
         for (PickNumber pickNumber : game.pickNumbers)
             pickNumber.onRandom(random(1, dxdsText.length, 1));
         game.notifyListener();
     }

    //五星和值大小单双随机 wuxinghezhidaxiaodanshuang
    public static void wuxinghezhidaxiaodanshuang495Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, dxdsText.length, 1));
        game.notifyListener();
    }

    //FC3D
    //百十随机 bs
    public static void bs372Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, funText.length, 1));
        game.notifyListener();
    }

    //百个随机 bg
    public static void bg373Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, funText.length, 1));
        game.notifyListener();
    }

    //十个随机 sg
    public static void sg374Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, funText.length, 1));
        game.notifyListener();
    }

}
