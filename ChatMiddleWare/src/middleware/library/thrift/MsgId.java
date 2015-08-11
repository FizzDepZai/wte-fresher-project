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
 * Message Id struct
 */
public class MsgId implements org.apache.thrift.TBase<MsgId, MsgId._Fields>, java.io.Serializable, Cloneable, Comparable<MsgId> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MsgId");

  private static final org.apache.thrift.protocol.TField MSG_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("msgId", org.apache.thrift.protocol.TType.STRING, (short)1);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new MsgIdStandardSchemeFactory());
    schemes.put(TupleScheme.class, new MsgIdTupleSchemeFactory());
  }

  public String msgId; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    MSG_ID((short)1, "msgId");

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
        case 1: // MSG_ID
          return MSG_ID;
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
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.MSG_ID, new org.apache.thrift.meta_data.FieldMetaData("msgId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MsgId.class, metaDataMap);
  }

  public MsgId() {
  }

  public MsgId(
    String msgId)
  {
    this();
    this.msgId = msgId;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MsgId(MsgId other) {
    if (other.isSetMsgId()) {
      this.msgId = other.msgId;
    }
  }

  public MsgId deepCopy() {
    return new MsgId(this);
  }

  @Override
  public void clear() {
    this.msgId = null;
  }

  public String getMsgId() {
    return this.msgId;
  }

  public MsgId setMsgId(String msgId) {
    this.msgId = msgId;
    return this;
  }

  public void unsetMsgId() {
    this.msgId = null;
  }

  /** Returns true if field msgId is set (has been assigned a value) and false otherwise */
  public boolean isSetMsgId() {
    return this.msgId != null;
  }

  public void setMsgIdIsSet(boolean value) {
    if (!value) {
      this.msgId = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case MSG_ID:
      if (value == null) {
        unsetMsgId();
      } else {
        setMsgId((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case MSG_ID:
      return getMsgId();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case MSG_ID:
      return isSetMsgId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof MsgId)
      return this.equals((MsgId)that);
    return false;
  }

  public boolean equals(MsgId that) {
    if (that == null)
      return false;

    boolean this_present_msgId = true && this.isSetMsgId();
    boolean that_present_msgId = true && that.isSetMsgId();
    if (this_present_msgId || that_present_msgId) {
      if (!(this_present_msgId && that_present_msgId))
        return false;
      if (!this.msgId.equals(that.msgId))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(MsgId other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetMsgId()).compareTo(other.isSetMsgId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMsgId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.msgId, other.msgId);
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
    StringBuilder sb = new StringBuilder("MsgId(");
    boolean first = true;

    sb.append("msgId:");
    if (this.msgId == null) {
      sb.append("null");
    } else {
      sb.append(this.msgId);
    }
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class MsgIdStandardSchemeFactory implements SchemeFactory {
    public MsgIdStandardScheme getScheme() {
      return new MsgIdStandardScheme();
    }
  }

  private static class MsgIdStandardScheme extends StandardScheme<MsgId> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, MsgId struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // MSG_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.msgId = iprot.readString();
              struct.setMsgIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, MsgId struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.msgId != null) {
        oprot.writeFieldBegin(MSG_ID_FIELD_DESC);
        oprot.writeString(struct.msgId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MsgIdTupleSchemeFactory implements SchemeFactory {
    public MsgIdTupleScheme getScheme() {
      return new MsgIdTupleScheme();
    }
  }

  private static class MsgIdTupleScheme extends TupleScheme<MsgId> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MsgId struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetMsgId()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetMsgId()) {
        oprot.writeString(struct.msgId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MsgId struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.msgId = iprot.readString();
        struct.setMsgIdIsSet(true);
      }
    }
  }

}

