package elegant.access.apidemo;

import junit.framework.TestCase;

import org.junit.Test;

import elegant.access.apidemo.legacy.jsoner.AdmobConfigResponse;
import elegant.access.apidemo.legacy.jsoner.Jsoner;

class JavaJsonNullUnitTest {

    @Test
    public void jsonNullTest(){
        AdmobConfigResponse admobConfigResponse = Jsoner.getInstance().fromJson( makeHardcodeResponse(), AdmobConfigResponse.class);

        TestCase.assertNull("Verify that object is null", admobConfigResponse.code);

    }

    private String makeHardcodeResponse() {
        return "{\n" +
                "\"country\":\"tw\",\n" +
                "\"key\":\"airdroid_admob\",\n" +
                "\"account_id\":\"191550616\",\n" +
                "\"androidid\":\"c1a4f8911c377183\",\n" +
                "\"app_channel\":\"channel\",\n" +
                "\"app_ver\":30325,\n" +
                "\"app_ver_name\":\"4.2.9.5\",\n" +
                "\"beta\":false,\n" +
                "\"channel\":\"channel\",\n" +
                "\"device_id\":\"e4567d2510913fe797c9ca31bf709957\",\n" +
                "\"imei\":\"4db4926498745f815299b69bcb74315e\",\n" +
                "\"language\":\"zh-TW\",\n" +
                "\"logic_key\":\"1168ae1c8458bedb02f8f73c8822e1bb\",\n" +
                "\"mac\":\"\",\n" +
                "\"manu\":\"samsung\",\n" +
                "\"model\":\"SM-G900I\",\n" +
                "\"nickname\":\"willy.chen\",\n" +
                "\"os_ver\":23,\n" +
                "\"package_id\":\"com.sand.airdroid\",\n" +
                "\"package_name\":\"AirDroid\",\n" +
                "\"rom_info\":\"MMB29M.G900IZTU1CPI2\",\n" +
                "\"unique_id\":\"null\",\n" +
                "\"code\":null\n" +
                "}";
    }

}
