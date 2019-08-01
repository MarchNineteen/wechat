package com.wyb.mp.api.impl;

import com.wyb.common.api.WxConsts;
import com.wyb.common.bean.menu.WxMenu;
import com.wyb.common.bean.menu.WxMenuButton;
import com.wyb.common.exception.WxErrorException;
import com.wyb.mp.api.WxMpService;
import com.wyb.mp.bean.menu.WxMpGetSelfMenuInfoResult;
import com.wyb.mp.bean.menu.WxMpMenu;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * 测试菜单
 *
 */
public class WxMpMenuServiceImplTest {

  protected WxMpService wxService;
  private String menuId = null;

  @Before
  public void initService() {
    wxService = new WxMpServiceImpl();
    wxService.setWxMpConfigStorage(
            new WxMpInMemoryConfigStorage("wx555e719fb5428862", "f3aafcdeb913a5990893854f2938f204"));
  }

  @Test
  public void testMenuCreate() throws WxErrorException {
    WxMenu wxMenu = getMenu();
    System.out.println(wxMenu.toJson());
    this.wxService.getMenuService().menuCreate(wxMenu);
  }

  @Test
  public void testMenuTryMatch() throws Exception {
    WxMenu menu = this.wxService.getMenuService().menuTryMatch("...");
    System.out.println(menu);
  }

  @Test
  public void testGetSelfMenuInfo() throws Exception {
    WxMpGetSelfMenuInfoResult selfMenuInfo = this.wxService.getMenuService().getSelfMenuInfo();
    System.out.println(selfMenuInfo);
  }

  @Test
  public void testCreateConditionalMenu() throws WxErrorException {
    String json = "{\n" +
      " 	\"button\":[\n" +
      " 	{	\n" +
      "    	\"type\":\"click\",\n" +
      "    	\"name\":\"今日歌曲\",\n" +
      "     	\"key\":\"V1001_TODAY_MUSIC\" \n" +
      "	},\n" +
      "	{ \n" +
      "		\"name\":\"菜单\",\n" +
      "		\"sub_button\":[\n" +
      "		{	\n" +
      "			\"type\":\"view\",\n" +
      "			\"name\":\"搜索\",\n" +
      "			\"url\":\"http://www.soso.com/\"\n" +
      "		},\n" +
      "		{\n" +
      "			\"type\":\"view\",\n" +
      "			\"name\":\"视频\",\n" +
      "			\"url\":\"http://v.qq.com/\"\n" +
      "		},\n" +
      "		{\n" +
      "			\"type\":\"click\",\n" +
      "			\"name\":\"赞一下我们\",\n" +
      "			\"key\":\"V1001_GOOD\"\n" +
      "		}]\n" +
      " }],\n" +
      "\"matchrule\":{\n" +
      "  \"tag_id\":\"2\",\n" +
      "  \"sex\":\"1\",\n" +
      "  \"country\":\"中国\",\n" +
      "  \"province\":\"广东\",\n" +
      "  \"city\":\"广州\",\n" +
      "  \"client_platform_type\":\"2\",\n" +
      "  \"language\":\"zh_CN\"\n" +
      "  }\n" +
      "}";

    this.menuId = this.wxService.getMenuService().menuCreate(json);
    System.out.println(this.menuId);
  }

  @Test
  public void testMultiCreateConditionalMenu() throws WxErrorException {
    String json = "{\n" +
      " 	\"button\":[\n" +
      " 	{	\n" +
      "    	\"type\":\"click\",\n" +
      "    	\"name\":\"今日歌曲\",\n" +
      "     	\"key\":\"V1001_TODAY_MUSIC\" \n" +
      "	},\n" +
      "	{ \n" +
      "		\"name\":\"菜单\",\n" +
      "		\"sub_button\":[\n" +
      "		{	\n" +
      "			\"type\":\"view\",\n" +
      "			\"name\":\"搜索\",\n" +
      "			\"url\":\"http://www.soso.com/\"\n" +
      "		},\n" +
      "		{\n" +
      "			\"type\":\"view\",\n" +
      "			\"name\":\"视频\",\n" +
      "			\"url\":\"http://v.qq.com/\"\n" +
      "		},\n" +
      "		{\n" +
      "			\"type\":\"click\",\n" +
      "			\"name\":\"赞一下我们\",\n" +
      "			\"key\":\"V1001_GOOD\"\n" +
      "		}]\n" +
      " }],\n" +
      "\"matchrule\":{\n" +
      "  \"tag_id\":\"2\",\n" +
      "  \"sex\":\"1\",\n" +
      "  \"country\":\"中国\",\n" +
      "  \"province\":\"广东\",\n" +
      "  \"city\":\"广州\",\n" +
      "  \"client_platform_type\":\"2\",\n" +
      "  \"language\":\"zh_CN\"\n" +
      "  }\n" +
      "}";
    this.menuId = this.wxService.getMenuService().menuCreate(json);
    System.out.println(this.menuId);
  }

  @Test()
  public void testMenuGet_AfterCreateConditionalMenu() throws WxErrorException {
    WxMpMenu wxMenu = this.wxService.getMenuService().menuGet();
    assertNotNull(wxMenu);
    System.out.println(wxMenu.toJson());
    assertNotNull(wxMenu.getConditionalMenu().get(0).getRule().getTagId());
  }

  @Test()
  public void testDeleteConditionalMenu() throws WxErrorException {
    this.wxService.getMenuService().menuDelete(menuId);
  }

  @Test
  public void testCreateMenu_by_json() throws WxErrorException {
    String a = "{\n" +
      "  \"button\": [\n" +
      "    {\n" +
      "      \"type\": \"click\",\n" +
      "      \"name\": \"今日歌曲\",\n" +
      "      \"key\": \"V1001_TODAY_MUSIC\"\n" +
      "    },\n" +
      "    {\n" +
      "      \"name\": \"菜单\",\n" +
      "      \"sub_button\": [\n" +
      "        {\n" +
      "          \"type\": \"view\",\n" +
      "          \"name\": \"搜索\",\n" +
      "          \"url\": \"http://www.soso.com/\"\n" +
      "        },\n" +
      "        {\n" +
      "          \"type\": \"miniprogram\",\n" +
      "          \"name\": \"wxa\",\n" +
      "          \"url\": \"http://mp.weixin.qq.com\",\n" +
      "          \"appid\": \"wx286b93c14bbf93aa\",\n" +
      "          \"pagepath\": \"pages/lunar/index.html\"\n" +
      "        },\n" +
      "        {\n" +
      "          \"type\": \"click\",\n" +
      "          \"name\": \"赞一下我们\",\n" +
      "          \"key\": \"V1001_GOOD\"\n" +
      "        }\n" +
      "      ]\n" +
      "    }\n" +
      "  ]\n" +
      "}";

    WxMenu menu = WxMenu.fromJson(a);
    System.out.println(menu.toJson());
    this.wxService.getMenuService().menuCreate(menu);
  }

  @Test
  public void testMenuGet() throws WxErrorException {
    WxMpMenu wxMenu = this.wxService.getMenuService().menuGet();
    assertNotNull(wxMenu);
    System.out.println(wxMenu.toJson());
  }

  @Test
  public void testMenuDelete() throws WxErrorException {
    this.wxService.getMenuService().menuDelete();
  }

  public WxMenu getMenu() {
    WxMenu menu = new WxMenu();
    WxMenuButton button1 = new WxMenuButton();
    button1.setType(WxConsts.MenuButtonType.CLICK);
    button1.setName("今日歌曲");
    button1.setKey("V1001_TODAY_MUSIC");

//    WxMenuButton button2 = new WxMenuButton();
//    button2.setType(WxConsts.MenuButtonType.MINIPROGRAM);
//    button2.setName("小程序");
//    button2.setAppId("wx286b93c14bbf93aa");
//    button2.setPagePath("pages/lunar/index.html");
//    button2.setUrl("http://mp.weixin.qq.com");

    WxMenuButton button3 = new WxMenuButton();
    button3.setName("菜单");

    menu.getButtons().add(button1);
//    menu.getButtons().add(button2);
    menu.getButtons().add(button3);

    WxMenuButton button31 = new WxMenuButton();
    button31.setType(WxConsts.MenuButtonType.VIEW);
    button31.setName("搜索");
    button31.setUrl("http://www.soso.com/");

    WxMenuButton button32 = new WxMenuButton();
    button32.setType(WxConsts.MenuButtonType.VIEW);
    button32.setName("视频");
    button32.setUrl("http://v.qq.com/");

    WxMenuButton button33 = new WxMenuButton();
    button33.setType(WxConsts.MenuButtonType.CLICK);
    button33.setName("赞一下我们");
    button33.setKey("V1001_GOOD");

    button3.getSubButtons().add(button31);
    button3.getSubButtons().add(button32);
    button3.getSubButtons().add(button33);

    return menu;

  }


}
