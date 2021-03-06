/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package middleware.library.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Emotion statistic by day(system time)
 */
public class EmotionStatistic implements org.apache.thrift.TBase<EmotionStatistic, EmotionStatistic._Fields>, java.io.Serializable, Cloneable, Comparable<EmotionStatistic> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("EmotionStatistic");

  private static final org.apache.thrift.protocol.TField EMOTION_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("emotionId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField DATE_FIELD_DESC = new org.apache.thrift.protocol.TField("date", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField USED_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("usedCount", org.apache.thrift.protocol.TType.I32, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new EmotionStatisticStandardSchemeFactory());
    schemes.put(TupleScheme.class, new EmotionStatisticTupleSchemeFactory());
  }

  public String emotionId; // required
  public int date; // required
  public int usedCount; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    EMOTION_ID((short)1, "emotionId"),
    DATE((short)2, "date"),
    USED_COUNT((short)3, "usedCount");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // EMOTION_ID
          return EMOTION_ID;
        case 2: // DATE
          return DATE;
        case 3: // USED_COUNT
          return USED_COUNT;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __DATE_ISSET_ID = 0;
  private static final int __USEDCOUNT_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.EMOTION_ID, new org.apache.thrift.meta_data.FieldMetaData("emotionId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DATE, new org.apache.thrift.meta_data.FieldMetaData("date", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.USED_COUNT, new org.apache.thrift.meta_data.FieldMetaData("usedCount", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(EmotionStatistic.class, metaDataMap);
  }

  public EmotionStatistic() {
  }

  public EmotionStatistic(
    String emotionId,
    int date,
    int usedCount)
  {
    this();
    this.emotionId = emotionId;
    this.date = date;
    setDateIsSet(true);
    this.usedCount = usedCount;
    setUsedCountIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public EmotionStatistic(EmotionStatistic other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetEmotionId()) {
      this.emotionId = other.emotionId;
    }
    this.date = other.date;
    this.usedCount = other.usedCount;
  }

  public EmotionStatistic deepCopy() {
    return new EmotionStatistic(this);
  }

  @Override
  public void clear() {
    this.emotionId = null;
    setDateIsSet(false);
    this.date = 0;
    setUsedCountIsSet(false);
    this.usedCount = 0;
  }

  public String getEmotionId() {
    return this.emotionId;
  }

  public EmotionStatistic setEmotionId(String emotionId) {
    this.emotionId = emotionId;
    return this;
  }

  public void unsetEmotionId() {
    this.emotionId = null;
  }

  /** Returns true if field emotionId is set (has been assigned a value) and false otherwise */
  public boolean isSetEmotionId() {
    return this.emotionId != null;
  }

  public void setEmotionIdIsSet(boolean value) {
    if (!value) {
      this.emotionId = null;
    }
  }

  public int getDate() {
    return this.date;
  }

  public EmotionStatistic setDate(int date) {
    this.date = date;
    setDateIsSet(true);
    return this;
  }

  public void unsetDate() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __DATE_ISSET_ID);
  }

  /** Returns true if field date is set (has been assigned a value) and false otherwise */
  public boolean isSetDate() {
    return EncodingUtils.testBit(__isset_bitfield, __DATE_ISSET_ID);
  }

  public void setDateIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __DATE_ISSET_ID, value);
  }

  public int getUsedCount() {
    return this.usedCount;
  }

  public EmotionStatistic setUsedCount(int usedCount) {
    this.usedCount = usedCount;
    setUsedCountIsSet(true);
    return this;
  }

  public void unsetUsedCount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __USEDCOUNT_ISSET_ID);
  }

  /** Returns true if field usedCount is set (has been assigned a value) and false otherwise */
  public boolean isSetUsedCount() {
    return EncodingUtils.testBit(__isset_bitfield, __USEDCOUNT_ISSET_ID);
  }

  public void setUsedCountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __USEDCOUNT_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case EMOTION_ID:
      if (value == null) {
        unsetEmotionId();
      } else {
        setEmotionId((String)value);
      }
      break;

    case DATE:
      if (value == null) {
        unsetDate();
      } else {
        setDate((Integer)value);
      }
      break;

    case USED_COUNT:
      if (value == null) {
        unsetUsedCount();
      } else {
        setUsedCount((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case EMOTION_ID:
      return getEmotionId();

    case DATE:
      return Integer.valueOf(getDate());

    case USED_COUNT:
      return Integer.valueOf(getUsedCount());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case EMOTION_ID:
      return isSetEmotionId();
    case DATE:
      return isSetDate();
    case USED_COUNT:
      return isSetUsedCount();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof EmotionStatistic)
      return this.equals((EmotionStatistic)that);
    return false;
  }

  public boolean equals(EmotionStatistic that) {
    if (that == null)
      return false;

    boolean this_present_emotionId = true && this.isSetEmotionId();
    boolean that_present_emotionId = true && that.isSetEmotionId();
    if (this_present_emotionId || that_present_emotionId) {
      if (!(this_present_emotionId && that_present_emotionId))
        return false;
      if (!this.emotionId.equals(that.emotionId))
        return false;
    }

    boolean this_present_date = true;
    boolean that_present_date = true;
    if (this_present_date || that_present_date) {
      if (!(this_present_date && that_present_date))
        return false;
      if (this.date != that.date)
        return false;
    }

    boolean this_present_usedCount = true;
    boolean that_present_usedCount = true;
    if (this_present_usedCount || that_present_usedCount) {
      if (!(this_present_usedCount && that_present_usedCount))
        return false;
      if (this.usedCount != that.usedCount)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(EmotionStatistic other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetEmotionId()).compareTo(other.isSetEmotionId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEmotionId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.emotionId, other.emotionId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDate()).compareTo(other.isSetDate());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDate()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.date, other.date);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUsedCount()).compareTo(other.isSetUsedCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUsedCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.usedCount, other.usedCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("EmotionStatistic(");
    boolean first = true;

    sb.append("emotionId:");
    if (this.emotionId == null) {
      sb.append("null");
    } else {
      sb.append(this.emotionId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("date:");
    sb.append(this.date);
    first = false;
    if (!first) sb.append(", ");
    sb.append("usedCount:");
    sb.append(this.usedCount);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class EmotionStatisticStandardSchemeFactory implements SchemeFactory {
    public EmotionStatisticStandardScheme getScheme() {
      return new EmotionStatisticStandardScheme();
    }
  }

  private static class EmotionStatisticStandardScheme extends StandardScheme<EmotionStatistic> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, EmotionStatistic struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // EMOTION_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.emotionId = iprot.readString();
              struct.setEmotionIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DATE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.date = iprot.readI32();
              struct.setDateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // USED_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.usedCount = iprot.readI32();
              struct.setUsedCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, EmotionStatistic struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.emotionId != null) {
        oprot.writeFieldBegin(EMOTION_ID_FIELD_DESC);
        oprot.writeString(struct.emotionId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(DATE_FIELD_DESC);
      oprot.writeI32(struct.date);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(USED_COUNT_FIELD_DESC);
      oprot.writeI32(struct.usedCount);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class EmotionStatisticTupleSchemeFactory implements SchemeFactory {
    public EmotionStatisticTupleScheme getScheme() {
      return new EmotionStatisticTupleScheme();
    }
  }

  private static class EmotionStatisticTupleScheme extends TupleScheme<EmotionStatistic> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, EmotionStatistic struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetEmotionId()) {
        optionals.set(0);
      }
      if (struct.isSetDate()) {
        optionals.set(1);
      }
      if (struct.isSetUsedCount()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetEmotionId()) {
        oprot.writeString(struct.emotionId);
      }
      if (struct.isSetDate()) {
        oprot.writeI32(struct.date);
      }
      if (struct.isSetUsedCount()) {
        oprot.writeI32(struct.usedCount);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, EmotionStatistic struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.emotionId = iprot.readString();
        struct.setEmotionIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.date = iprot.readI32();
        struct.setDateIsSet(true);
      }
      if (incoming.get(2)) {
        struct.usedCount = iprot.readI32();
        struct.setUsedCountIsSet(true);
      }
    }
  }

}

