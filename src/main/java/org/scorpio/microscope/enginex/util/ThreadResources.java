package org.scorpio.microscope.enginex.util;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * Title: �߳���Դ������
 * </p>
 * <p>
 * <p>
 * Description:
 * </p>
 * <p>
 * <p>
 * Company: �����ź��ǿƼ��ɷ����޹�˾
 * </p>
 *
 * @author ���ڼ�
 * @since��2009-8-11 ����11:51:41
 */
public abstract class ThreadResources {

    private static final ThreadLocal resources = new ThreadLocal();

    public static Object getResource(Object actualKey) {
        Object value = doGetResource(actualKey);
        return value;
    }

    public static void bindResource(Object actualKey, Object value)
            throws IllegalStateException {
        Map map = (Map) resources.get();
        // set ThreadLocal Map if none found
        if (map == null) {
            map = new HashMap();
            resources.set(map);
        }
        map.put(actualKey, value);
    }

    public static Object unbindResource(Object actualKey)
            throws IllegalStateException {
        Object value = doUnbindResource(actualKey);
        return value;
    }

    private static Object doUnbindResource(Object actualKey) {
        Map map = (Map) resources.get();
        if (map == null) {
            return null;
        }
        Object value = map.remove(actualKey);
        // Remove entire ThreadLocal if empty...
        if (map.isEmpty()) {
            resources.set(null);
        }
        return value;
    }

    public static boolean hasResource(Object actualKey) {
        Object value = doGetResource(actualKey);
        return (value != null);
    }

    private static Object doGetResource(Object actualKey) {
        Map map = (Map) resources.get();
        if (map == null) {
            return null;
        }
        return map.get(actualKey);
    }

    public static void clear() {
        resources.set(null);
    }

}
