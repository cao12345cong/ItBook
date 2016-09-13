/*
 * 文件名：ButtonAndText.java
 * 描述：TODO
 * 修改人：王鹏
 * 修改时间：下午7:04:53
 * 修改内容：待定.
 * @since 
 */
package com.xtx.itbook.entity;


/**
 * 
 * @author Abelart.
 */
public class ButtonAndText
{
    public int imgId;        //最终 此图为从服务器上获取  是为 图片的url.
    
    public String texId;

    /**
     * 构造方法 .
     */
    public ButtonAndText()
    {
        super();
        
    }

    /**
     * 构造方法 @param imgId
     * 构造方法 @param texId.
     */
    public ButtonAndText(int imgId, String texId)
    {
        super();
        this.imgId = imgId;
        this.texId = texId;
    }

    public int getImgId()
    {
        return imgId;
    }

    public void setImgId(int imgId)
    {
        this.imgId = imgId;
    }

    public String getTexId()
    {
        return texId;
    }

    public void setTexId(String texId)
    {
        this.texId = texId;
    }

    
    
    
    
}
