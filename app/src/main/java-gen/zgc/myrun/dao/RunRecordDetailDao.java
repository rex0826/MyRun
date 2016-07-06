package zgc.myrun.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import zgc.myrun.bean.RunRecordDetail;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "RUN_RECORD_DETAIL".
*/
public class RunRecordDetailDao extends AbstractDao<RunRecordDetail, Long> {

    public static final String TABLENAME = "RUN_RECORD_DETAIL";

    /**
     * Properties of entity RunRecordDetail.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property LonginName = new Property(1, String.class, "LonginName", false, "LONGIN_NAME");
        public final static Property RunTiming = new Property(2, java.util.Date.class, "RunTiming", false, "RUN_TIMING");
        public final static Property RunID = new Property(3, String.class, "RunID", false, "RUN_ID");
        public final static Property Position = new Property(4, String.class, "Position", false, "POSITION");
        public final static Property Longitude = new Property(5, Float.class, "Longitude", false, "LONGITUDE");
        public final static Property Latitude = new Property(6, Float.class, "Latitude", false, "LATITUDE");
        public final static Property RunDistance = new Property(7, Float.class, "RunDistance", false, "RUN_DISTANCE");
        public final static Property AverageSpeed = new Property(8, Float.class, "AverageSpeed", false, "AVERAGE_SPEED");
        public final static Property StepNumber = new Property(9, Long.class, "StepNumber", false, "STEP_NUMBER");
    };


    public RunRecordDetailDao(DaoConfig config) {
        super(config);
    }
    
    public RunRecordDetailDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"RUN_RECORD_DETAIL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"LONGIN_NAME\" TEXT NOT NULL ," + // 1: LonginName
                "\"RUN_TIMING\" INTEGER NOT NULL ," + // 2: RunTiming
                "\"RUN_ID\" TEXT," + // 3: RunID
                "\"POSITION\" TEXT," + // 4: Position
                "\"LONGITUDE\" REAL," + // 5: Longitude
                "\"LATITUDE\" REAL," + // 6: Latitude
                "\"RUN_DISTANCE\" REAL," + // 7: RunDistance
                "\"AVERAGE_SPEED\" REAL," + // 8: AverageSpeed
                "\"STEP_NUMBER\" INTEGER);"); // 9: StepNumber
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"RUN_RECORD_DETAIL\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, RunRecordDetail entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getLonginName());
        stmt.bindLong(3, entity.getRunTiming().getTime());
 
        String RunID = entity.getRunID();
        if (RunID != null) {
            stmt.bindString(4, RunID);
        }
 
        String Position = entity.getPosition();
        if (Position != null) {
            stmt.bindString(5, Position);
        }
 
        Float Longitude = entity.getLongitude();
        if (Longitude != null) {
            stmt.bindDouble(6, Longitude);
        }
 
        Float Latitude = entity.getLatitude();
        if (Latitude != null) {
            stmt.bindDouble(7, Latitude);
        }
 
        Float RunDistance = entity.getRunDistance();
        if (RunDistance != null) {
            stmt.bindDouble(8, RunDistance);
        }
 
        Float AverageSpeed = entity.getAverageSpeed();
        if (AverageSpeed != null) {
            stmt.bindDouble(9, AverageSpeed);
        }
 
        Long StepNumber = entity.getStepNumber();
        if (StepNumber != null) {
            stmt.bindLong(10, StepNumber);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public RunRecordDetail readEntity(Cursor cursor, int offset) {
        RunRecordDetail entity = new RunRecordDetail( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // LonginName
            new java.util.Date(cursor.getLong(offset + 2)), // RunTiming
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // RunID
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // Position
            cursor.isNull(offset + 5) ? null : cursor.getFloat(offset + 5), // Longitude
            cursor.isNull(offset + 6) ? null : cursor.getFloat(offset + 6), // Latitude
            cursor.isNull(offset + 7) ? null : cursor.getFloat(offset + 7), // RunDistance
            cursor.isNull(offset + 8) ? null : cursor.getFloat(offset + 8), // AverageSpeed
            cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9) // StepNumber
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, RunRecordDetail entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setLonginName(cursor.getString(offset + 1));
        entity.setRunTiming(new java.util.Date(cursor.getLong(offset + 2)));
        entity.setRunID(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPosition(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setLongitude(cursor.isNull(offset + 5) ? null : cursor.getFloat(offset + 5));
        entity.setLatitude(cursor.isNull(offset + 6) ? null : cursor.getFloat(offset + 6));
        entity.setRunDistance(cursor.isNull(offset + 7) ? null : cursor.getFloat(offset + 7));
        entity.setAverageSpeed(cursor.isNull(offset + 8) ? null : cursor.getFloat(offset + 8));
        entity.setStepNumber(cursor.isNull(offset + 9) ? null : cursor.getLong(offset + 9));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(RunRecordDetail entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(RunRecordDetail entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}