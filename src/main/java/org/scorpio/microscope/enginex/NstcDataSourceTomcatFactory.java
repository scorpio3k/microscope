package org.scorpio.microscope.enginex;

import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import javax.sql.DataSource;

import org.scorpio.microscope.enginex.core.Setting;


public class NstcDataSourceTomcatFactory implements ObjectFactory {
    private static final String[] ALL_PROPERTIES = {"driverClassName",
            "password", "url", "username",};

    public Object getObjectInstance(Object obj, Name name, Context nameCtx,
                                    Hashtable environment) throws Exception {
        if ((obj == null) || (!(obj instanceof Reference))) {
            return null;
        }
        Reference ref = (Reference) obj;
        if (!("javax.sql.DataSource".equals(ref.getClassName()))) {
            return null;
        }

        Properties properties = new Properties();
        for (int i = 0; i < ALL_PROPERTIES.length; ++i) {
            String propertyName = ALL_PROPERTIES[i];
            RefAddr ra = ref.get(propertyName);
            if (ra != null) {
                String propertyValue = ra.getContent().toString();
                properties.setProperty(propertyName, propertyValue);
            }
        }

        return createDataSource(properties);
    }

    public static DataSource createDataSource(Properties properties)
            throws Exception {
        System.out.println("׼������P6���ӳ�...........");
        NstcDbcpDataSource dataSource = new org.scorpio.microscope.enginex.NstcDbcpDataSource();

        String value = null;
        value = properties.getProperty("driverClassName");
        if (value != null) {
            dataSource.setDriverClassName(value);
        }
        value = properties.getProperty("password");
        if (value != null) {
            dataSource.setPassword(value);
        }
        value = properties.getProperty("url");
        if (value != null) {
            dataSource.setUrl(value);
        }
        value = properties.getProperty("username");
        if (value != null) {
            dataSource.setUsername(value);
        }
        value = properties.getProperty("checkLeak");
        if (value != null && "false".equalsIgnoreCase(value)) {
            Setting.disableConnLeaks();
        } else {
            Setting.enableConnLeaks();
        }
        return dataSource;
    }
}
