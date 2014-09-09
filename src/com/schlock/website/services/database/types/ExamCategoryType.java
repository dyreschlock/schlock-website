package com.schlock.website.services.database.types;

import com.schlock.website.entities.apps.kendo.ExamCategory;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class ExamCategoryType implements UserType
{
    private static final int[] TYPES = { Types.VARCHAR };

    public int[] sqlTypes()
    {
        return TYPES;
    }

    public Class returnedClass()
    {
        return ExamCategory.class;
    }

    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException
    {
        String name = rs.getString(names[0]);
        ExamCategory category = null;
        if (!rs.wasNull())
        {
            category = ExamCategory.valueOf(name);
        }
        return category;
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException
    {
        if (value == null)
        {
            st.setNull(index, Types.VARCHAR);
        }
        else
        {
            st.setString(index, ((ExamCategory) value).name());
        }
    }

    public Object deepCopy(Object value) throws HibernateException
    {
        return value;
    }

    public boolean isMutable()
    {
        return false;
    }

    public Object assemble(Serializable cached, Object owner) throws HibernateException
    {
        return cached;
    }

    public Serializable disassemble(Object value) throws HibernateException
    {
        return (Serializable) value;
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException
    {
        return original;
    }

    public int hashCode(Object x) throws HibernateException
    {
        return x.hashCode();
    }

    public boolean equals(Object x, Object y) throws HibernateException
    {
        if (x == y)
        {
            return true;
        }
        if (x == null || y == null)
        {
            return false;
        }
        return x.equals(y);
    }
}
