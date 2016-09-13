/*
 * 文件名：DataInterface.java
 * 描述：TODO
 * 修改人：王鹏
 * 修改时间：上午11:45:44
 * 修改内容：待定.
 * @since 
 */
package com.xtx.itbook.data;

/**
 * 
 * @author Abelart.
 */
public abstract interface DataInterface
{
    /**参数校验，**/
    public void checkParams(Object... params);

    public void checkApp();
}
