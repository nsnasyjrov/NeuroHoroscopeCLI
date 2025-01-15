package Horoscope;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class VKExampleAPI {
    static String access_token = "vk1.a.kBYMm2M0ni1BZPNpn-UUeBrcFVncaRxBSJVkdrI0up6LJGRt5NeVrTrc8-DUpEabxUqJqmp5wt4uvLYdJ0FA3rTiP1f1boa5X8Fb56sjs0_q_rC7StLr5Oj9M1qKcbD2b_CG46dqE8c7IOGVxpziyDvbDT9VC3Mz8QZ-UTBzhoc_3lSrvXvUIan2mMp5hXmTe1pGHGS1ZhM_IcDzv_Da2g";
    static String groupID = "neural_horo";
    static String version = "5.131";
    static String url = String.format("https://api.vk.com/method/wall.get?owner_id=%s&count=4&acce" +
            "ss_token=%s&v=%s", groupID, access_token, version);

    public String createRequest() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        String info = "";

        // Выполнение запроса:
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                System.out.println("Данные успешно собраны!");
                info = response.body().string();
            } else {
                System.out.println("Ошибка: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return info;
    }
}