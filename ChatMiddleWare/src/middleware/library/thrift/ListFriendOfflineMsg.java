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
 * 
 */
public class ListFriendOfflineMsg implements org.apache.thrift.TBase<ListFriendOfflineMsg, ListFriendOfflineMsg._Fields>, java.io.Serializable, Cloneable, Comparable<ListFriendOfflineMsg> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ListFriendOfflineMsg");

  private static final org.apache.thrift.protocol.TField USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("userId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField LIST_FRIEND_OFFLINE_MSG_FIELD_DESC = new org.apache.thrift.protocol.TField("listFriendOfflineMsg", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ListFriendOfflineMsgStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ListFriendOfflineMsgTupleSchemeFactory());
  }

  public String userId; // required
  public List<String> listFriendOfflineMsg; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    USER_ID((short)1, "userId"),
    LIST_FRIEND_OFFLINE_MSG((short)2, "listFriendOfflineMsg");

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
        case 1: // USER_ID
          return USER_ID;
        case 2: // LIST_FRIEND_OFFLINE_MSG
          return LIST_FRIEND_OFFLINE_MSG;
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
    tmpMap.put(_Fields.USER_ID, new org.apache.thrift.meta_data.FieldMetaData("userId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.LIST_FRIEND_OFFLINE_MSG, new org.apache.thrift.meta_data.FieldMetaData("listFriendOfflineMsg", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ListFriendOfflineMsg.class, metaDataMap);
  }

  public ListFriendOfflineMsg() {
  }

  public ListFriendOfflineMsg(
    String userId,
    List<String> listFriendOfflineMsg)
  {
    this();
    this.userId = userId;
    this.listFriendOfflineMsg = listFriendOfflineMsg;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ListFriendOfflineMsg(ListFriendOfflineMsg other) {
    if (other.isSetUserId()) {
      this.userId = other.userId;
    }
    if (other.isSetListFriendOfflineMsg()) {
      List<String> __this__listFriendOfflineMsg = new ArrayList<String>(other.listFriendOfflineMsg);
      this.listFriendOfflineMsg = __this__listFriendOfflineMsg;
    }
  }

  public ListFriendOfflineMsg deepCopy() {
    return new ListFriendOfflineMsg(this);
  }

  @Override
  public void clear() {
    this.userId = null;
    this.listFriendOfflineMsg = null;
  }

  public String getUserId() {
    return this.userId;
  }

  public ListFriendOfflineMsg setUserId(String userId) {
    this.userId = userId;
    return this;
  }

  public void unsetUserId() {
    this.userId = null;
  }

  /** Returns true if field userId is set (has been assigned a value) and false otherwise */
  public boolean isSetUserId() {
    return this.userId != null;
  }

  public void setUserIdIsSet(boolean value) {
    if (!value) {
      this.userId = null;
    }
  }

  public int getListFriendOfflineMsgSize() {
    return (this.listFriendOfflineMsg == null) ? 0 : this.listFriendOfflineMsg.size();
  }

  public java.util.Iterator<String> getListFriendOfflineMsgIterator() {
    return (this.listFriendOfflineMsg == null) ? null : this.listFriendOfflineMsg.iterator();
  }

  public void addToListFriendOfflineMsg(String elem) {
    if (this.listFriendOfflineMsg == null) {
      this.listFriendOfflineMsg = new ArrayList<String>();
    }
    this.listFriendOfflineMsg.add(elem);
  }

  public List<String> getListFriendOfflineMsg() {
    return this.listFriendOfflineMsg;
  }

  public ListFriendOfflineMsg setListFriendOfflineMsg(List<String> listFriendOfflineMsg) {
    this.listFriendOfflineMsg = listFriendOfflineMsg;
    return this;
  }

  public void unsetListFriendOfflineMsg() {
    this.listFriendOfflineMsg = null;
  }

  /** Returns true if field listFriendOfflineMsg is set (has been assigned a value) and false otherwise */
  public boolean isSetListFriendOfflineMsg() {
    return this.listFriendOfflineMsg != null;
  }

  public void setListFriendOfflineMsgIsSet(boolean value) {
    if (!value) {
      this.listFriendOfflineMsg = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case USER_ID:
      if (value == null) {
        unsetUserId();
      } else {
        setUserId((String)value);
      }
      break;

    case LIST_FRIEND_OFFLINE_MSG:
      if (value == null) {
        unsetListFriendOfflineMsg();
      } else {
        setListFriendOfflineMsg((List<String>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case USER_ID:
      return getUserId();

    case LIST_FRIEND_OFFLINE_MSG:
      return getListFriendOfflineMsg();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case USER_ID:
      return isSetUserId();
    case LIST_FRIEND_OFFLINE_MSG:
      return isSetListFriendOfflineMsg();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ListFriendOfflineMsg)
      return this.equals((ListFriendOfflineMsg)that);
    return false;
  }

  public boolean equals(ListFriendOfflineMsg that) {
    if (that == null)
      return false;

    boolean this_present_userId = true && this.isSetUserId();
    boolean that_present_userId = true && that.isSetUserId();
    if (this_present_userId || that_present_userId) {
      if (!(this_present_userId && that_present_userId))
        return false;
      if (!this.userId.equals(that.userId))
        return false;
    }

    boolean this_present_listFriendOfflineMsg = true && this.isSetListFriendOfflineMsg();
    boolean that_present_listFriendOfflineMsg = true && that.isSetListFriendOfflineMsg();
    if (this_present_listFriendOfflineMsg || that_present_listFriendOfflineMsg) {
      if (!(this_present_listFriendOfflineMsg && that_present_listFriendOfflineMsg))
        return false;
      if (!this.listFriendOfflineMsg.equals(that.listFriendOfflineMsg))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(ListFriendOfflineMsg other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetUserId()).compareTo(other.isSetUserId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userId, other.userId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetListFriendOfflineMsg()).compareTo(other.isSetListFriendOfflineMsg());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetListFriendOfflineMsg()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.listFriendOfflineMsg, other.listFriendOfflineMsg);
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
    StringBuilder sb = new StringBuilder("ListFriendOfflineMsg(");
    boolean first = true;

    sb.append("userId:");
    if (this.userId == null) {
      sb.append("null");
    } else {
      sb.append(this.userId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("listFriendOfflineMsg:");
    if (this.listFriendOfflineMsg == null) {
      sb.append("null");
    } else {
      sb.append(this.listFriendOfflineMsg);
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

  private static class ListFriendOfflineMsgStandardSchemeFactory implements SchemeFactory {
    public ListFriendOfflineMsgStandardScheme getScheme() {
      return new ListFriendOfflineMsgStandardScheme();
    }
  }

  private static class ListFriendOfflineMsgStandardScheme extends StandardScheme<ListFriendOfflineMsg> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ListFriendOfflineMsg struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.userId = iprot.readString();
              struct.setUserIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // LIST_FRIEND_OFFLINE_MSG
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list24 = iprot.readListBegin();
                struct.listFriendOfflineMsg = new ArrayList<String>(_list24.size);
                for (int _i25 = 0; _i25 < _list24.size; ++_i25)
                {
                  String _elem26;
                  _elem26 = iprot.readString();
                  struct.listFriendOfflineMsg.add(_elem26);
                }
                iprot.readListEnd();
              }
              struct.setListFriendOfflineMsgIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ListFriendOfflineMsg struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.userId != null) {
        oprot.writeFieldBegin(USER_ID_FIELD_DESC);
        oprot.writeString(struct.userId);
        oprot.writeFieldEnd();
      }
      if (struct.listFriendOfflineMsg != null) {
        oprot.writeFieldBegin(LIST_FRIEND_OFFLINE_MSG_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, struct.listFriendOfflineMsg.size()));
          for (String _iter27 : struct.listFriendOfflineMsg)
          {
            oprot.writeString(_iter27);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ListFriendOfflineMsgTupleSchemeFactory implements SchemeFactory {
    public ListFriendOfflineMsgTupleScheme getScheme() {
      return new ListFriendOfflineMsgTupleScheme();
    }
  }

  private static class ListFriendOfflineMsgTupleScheme extends TupleScheme<ListFriendOfflineMsg> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ListFriendOfflineMsg struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetUserId()) {
        optionals.set(0);
      }
      if (struct.isSetListFriendOfflineMsg()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetUserId()) {
        oprot.writeString(struct.userId);
      }
      if (struct.isSetListFriendOfflineMsg()) {
        {
          oprot.writeI32(struct.listFriendOfflineMsg.size());
          for (String _iter28 : struct.listFriendOfflineMsg)
          {
            oprot.writeString(_iter28);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ListFriendOfflineMsg struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.userId = iprot.readString();
        struct.setUserIdIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list29 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.listFriendOfflineMsg = new ArrayList<String>(_list29.size);
          for (int _i30 = 0; _i30 < _list29.size; ++_i30)
          {
            String _elem31;
            _elem31 = iprot.readString();
            struct.listFriendOfflineMsg.add(_elem31);
          }
        }
        struct.setListFriendOfflineMsgIsSet(true);
      }
    }
  }

}

