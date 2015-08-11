/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package business.library.thrift;

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
 * 
 */
public class EmotionId implements org.apache.thrift.TBase<EmotionId, EmotionId._Fields>, java.io.Serializable, Cloneable, Comparable<EmotionId> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("EmotionId");

  private static final org.apache.thrift.protocol.TField EMOTION_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("emotionId", org.apache.thrift.protocol.TType.I64, (short)1);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new EmotionIdStandardSchemeFactory());
    schemes.put(TupleScheme.class, new EmotionIdTupleSchemeFactory());
  }

  public long emotionId; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    EMOTION_ID((short)1, "emotionId");

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
  private static final int __EMOTIONID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.EMOTION_ID, new org.apache.thrift.meta_data.FieldMetaData("emotionId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(EmotionId.class, metaDataMap);
  }

  public EmotionId() {
  }

  public EmotionId(
    long emotionId)
  {
    this();
    this.emotionId = emotionId;
    setEmotionIdIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public EmotionId(EmotionId other) {
    __isset_bitfield = other.__isset_bitfield;
    this.emotionId = other.emotionId;
  }

  public EmotionId deepCopy() {
    return new EmotionId(this);
  }

  @Override
  public void clear() {
    setEmotionIdIsSet(false);
    this.emotionId = 0;
  }

  public long getEmotionId() {
    return this.emotionId;
  }

  public EmotionId setEmotionId(long emotionId) {
    this.emotionId = emotionId;
    setEmotionIdIsSet(true);
    return this;
  }

  public void unsetEmotionId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __EMOTIONID_ISSET_ID);
  }

  /** Returns true if field emotionId is set (has been assigned a value) and false otherwise */
  public boolean isSetEmotionId() {
    return EncodingUtils.testBit(__isset_bitfield, __EMOTIONID_ISSET_ID);
  }

  public void setEmotionIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __EMOTIONID_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case EMOTION_ID:
      if (value == null) {
        unsetEmotionId();
      } else {
        setEmotionId((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case EMOTION_ID:
      return Long.valueOf(getEmotionId());

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
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof EmotionId)
      return this.equals((EmotionId)that);
    return false;
  }

  public boolean equals(EmotionId that) {
    if (that == null)
      return false;

    boolean this_present_emotionId = true;
    boolean that_present_emotionId = true;
    if (this_present_emotionId || that_present_emotionId) {
      if (!(this_present_emotionId && that_present_emotionId))
        return false;
      if (this.emotionId != that.emotionId)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(EmotionId other) {
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
    StringBuilder sb = new StringBuilder("EmotionId(");
    boolean first = true;

    sb.append("emotionId:");
    sb.append(this.emotionId);
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

  private static class EmotionIdStandardSchemeFactory implements SchemeFactory {
    public EmotionIdStandardScheme getScheme() {
      return new EmotionIdStandardScheme();
    }
  }

  private static class EmotionIdStandardScheme extends StandardScheme<EmotionId> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, EmotionId struct) throws org.apache.thrift.TException {
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
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.emotionId = iprot.readI64();
              struct.setEmotionIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, EmotionId struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(EMOTION_ID_FIELD_DESC);
      oprot.writeI64(struct.emotionId);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class EmotionIdTupleSchemeFactory implements SchemeFactory {
    public EmotionIdTupleScheme getScheme() {
      return new EmotionIdTupleScheme();
    }
  }

  private static class EmotionIdTupleScheme extends TupleScheme<EmotionId> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, EmotionId struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetEmotionId()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetEmotionId()) {
        oprot.writeI64(struct.emotionId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, EmotionId struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.emotionId = iprot.readI64();
        struct.setEmotionIdIsSet(true);
      }
    }
  }

}

