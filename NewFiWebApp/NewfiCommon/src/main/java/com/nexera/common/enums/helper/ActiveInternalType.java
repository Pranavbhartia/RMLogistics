package com.nexera.common.enums.helper;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;

import com.nexera.common.enums.helperinterface.PersistentEnum;
import com.nexera.common.enums.ActiveInternalEnum;

public class ActiveInternalType extends PersistentEnumInternalType<ActiveInternalEnum> {
	
	@Override
    public Class<ActiveInternalEnum> returnedClass() {
        return ActiveInternalEnum.class;
    }

	@Override
    public Object nullSafeGet(ResultSet rs, String[] names,
            SessionImplementor session, Object owner)
            throws HibernateException, SQLException {
		 int id = rs.getInt(names[0]);
	        if(rs.wasNull()) {
	            return null;
	        }
	        for(PersistentEnum value : returnedClass().getEnumConstants()) {
	            if(id == value.getId()) {
	                return value;
	            }
	        }
	        throw new IllegalStateException("Unknown " + returnedClass().getSimpleName() + " id");
	    }
	 

	@Override
    public void nullSafeSet(PreparedStatement st, Object value, int index,
            SessionImplementor session) throws HibernateException, SQLException {
		 if (value == null) {
	            st.setNull(index, Types.INTEGER);
	        } else {
	            st.setInt(index, ((PersistentEnum)value).getId());
	        }
	    
    }
	
	
 

}

