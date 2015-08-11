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
 * Emotion group
 */
public class GroupEmotion implements org.apache.thrift.TBase<GroupEmotion, GroupEmotion._Fields>, java.io.Serializable, Cloneable, Comparable<GroupEmotion> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("GroupEmotion");

  private static final org.apache.thrift.protocol.TField GROUP_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("groupId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField GROUP_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("groupName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField EMOTION_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("emotionList", org.apache.thrift.protocol.TType.LIST, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new GroupEmotionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new GroupEmotionTupleSchemeFactory());
  }

  public String groupId; // required
  public String groupName; // required
  public List<EmotionItem> emotionList; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    GROUP_ID((short)1, "groupId"),
    GROUP_NAME((short)2, "groupName"),
    EMOTION_LIST((short)3, "emotionList");

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
        case 1: // GROUP_ID
          return GROUP_ID;
        case 2: // GROUP_NAME
          return GROUP_NAME;
        case 3: // EMOTION_LIST
          return EMOTION_LIST;
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
    tmpMap.put(_Fields.GROUP_ID, new org.apache.thrift.meta_data.FieldMetaData("groupId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.GROUP_NAME, new org.apache.thrift.meta_data.FieldMetaData("groupName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.EMOTION_LIST, new org.apache.thrift.meta_data.FieldMetaData("emotionList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, EmotionItem.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(GroupEmotion.class, metaDataMap);
  }

  public GroupEmotion() {
  }

  public GroupEmotion(
    String groupId,
    String groupName,
    List<EmotionItem> emotionList)
  {
    this();
    this.groupId = groupId;
    this.groupName = groupName;
    this.emotionList = emotionList;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GroupEmotion(GroupEmotion other) {
    if (other.isSetGroupId()) {
      this.groupId = other.groupId;
    }
    if (other.isSetGroupName()) {
      this.groupName = other.groupName;
    }
    if (other.isSetEmotionList()) {
      List<EmotionItem> __this__emotionList = new ArrayList<EmotionItem>(other.emotionList.size());
      for (EmotionItem other_element : other.emotionList) {
        __this__emotionList.add(new EmotionItem(other_element));
      }
      this.emotionList = __this__emotionList;
    }
  }

  public GroupEmotion deepCopy() {
    return new GroupEmotion(this);
  }

  @Override
  public void clear() {
    this.groupId = null;
    this.groupName = null;
    this.emotionList = null;
  }

  public String getGroupId() {
    return this.groupId;
  }

  public GroupEmotion setGroupId(String groupId) {
    this.groupId = groupId;
    return this;
  }

  public void unsetGroupId() {
    this.groupId = null;
  }

  /** Returns true if field groupId is set (has been assigned a value) and false otherwise */
  public boolean isSetGroupId() {
    return this.groupId != null;
  }

  public void setGroupIdIsSet(boolean value) {
    if (!value) {
      this.groupId = null;
    }
  }

  public String getGroupName() {
    return this.groupName;
  }

  public GroupEmotion setGroupName(String groupName) {
    this.groupName = groupName;
    return this;
  }

  public void unsetGroupName() {
    this.groupName = null;
  }

  /** Returns true if field groupName is set (has been assigned a value) and false otherwise */
  public boolean isSetGroupName() {
    return this.groupName != null;
  }

  public void setGroupNameIsSet(boolean value) {
    if (!value) {
      this.groupName = null;
    }
  }

  public int getEmotionListSize() {
    return (this.emotionList == null) ? 0 : this.emotionList.size();
  }

  public java.util.Iterator<EmotionItem> getEmotionListIterator() {
    return (this.emotionList == null) ? null : this.emotionList.iterator();
  }

  public void addToEmotionList(EmotionItem elem) {
    if (this.emotionList == null) {
      this.emotionList = new ArrayList<EmotionItem>();
    }
    this.emotionList.add(elem);
  }

  public List<EmotionItem> getEmotionList() {
    return this.emotionList;
  }

  public GroupEmotion setEmotionList(List<EmotionItem> emotionList) {
    this.emotionList = emotionList;
    return this;
  }

  public void unsetEmotionList() {
    this.emotionList = null;
  }

  /** Returns true if field emotionList is set (has been assigned a value) and false otherwise */
  public boolean isSetEmotionList() {
    return this.emotionList != null;
  }

  public void setEmotionListIsSet(boolean value) {
    if (!value) {
      this.emotionList = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case GROUP_ID:
      if (value == null) {
        unsetGroupId();
      } else {
        setGroupId((String)value);
      }
      break;

    case GROUP_NAME:
      if (value == null) {
        unsetGroupName();
      } else {
        setGroupName((String)value);
      }
      break;

    case EMOTION_LIST:
      if (value == null) {
        unsetEmotionList();
      } else {
        setEmotionList((List<EmotionItem>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case GROUP_ID:
      return getGroupId();

    case GROUP_NAME:
      return getGroupName();

    case EMOTION_LIST:
      return getEmotionList();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case GROUP_ID:
      return isSetGroupId();
    case GROUP_NAME:
      return isSetGroupName();
    case EMOTION_LIST:
      return isSetEmotionList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof GroupEmotion)
      return this.equals((GroupEmotion)that);
    return false;
  }

  public boolean equals(GroupEmotion that) {
    if (that == null)
      return false;

    boolean this_present_groupId = true && this.isSetGroupId();
    boolean that_present_groupId = true && that.isSetGroupId();
    if (this_present_groupId || that_present_groupId) {
      if (!(this_present_groupId && that_present_groupId))
        return false;
      if (!this.groupId.equals(that.groupId))
        return false;
    }

    boolean this_present_groupName = true && this.isSetGroupName();
    boolean that_present_groupName = true && that.isSetGroupName();
    if (this_present_groupName || that_present_groupName) {
      if (!(this_present_groupName && that_present_groupName))
        return false;
      if (!this.groupName.equals(that.groupName))
        return false;
    }

    boolean this_present_emotionList = true && this.isSetEmotionList();
    boolean that_present_emotionList = true && that.isSetEmotionList();
    if (this_present_emotionList || that_present_emotionList) {
      if (!(this_present_emotionList && that_present_emotionList))
        return false;
      if (!this.emotionList.equals(that.emotionList))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(GroupEmotion other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetGroupId()).compareTo(other.isSetGroupId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGroupId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.groupId, other.groupId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetGroupName()).compareTo(other.isSetGroupName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGroupName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.groupName, other.groupName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEmotionList()).compareTo(other.isSetEmotionList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEmotionList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.emotionList, other.emotionList);
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
    StringBuilder sb = new StringBuilder("GroupEmotion(");
    boolean first = true;

    sb.append("groupId:");
    if (this.groupId == null) {
      sb.append("null");
    } else {
      sb.append(this.groupId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("groupName:");
    if (this.groupName == null) {
      sb.append("null");
    } else {
      sb.append(this.groupName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("emotionList:");
    if (this.emotionList == null) {
      sb.append("null");
    } else {
      sb.append(this.emotionList);
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

  private static class GroupEmotionStandardSchemeFactory implements SchemeFactory {
    public GroupEmotionStandardScheme getScheme() {
      return new GroupEmotionStandardScheme();
    }
  }

  private static class GroupEmotionStandardScheme extends StandardScheme<GroupEmotion> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, GroupEmotion struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // GROUP_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.groupId = iprot.readString();
              struct.setGroupIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // GROUP_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.groupName = iprot.readString();
              struct.setGroupNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // EMOTION_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.emotionList = new ArrayList<EmotionItem>(_list0.size);
                for (int _i1 = 0; _i1 < _list0.size; ++_i1)
                {
                  EmotionItem _elem2;
                  _elem2 = new EmotionItem();
                  _elem2.read(iprot);
                  struct.emotionList.add(_elem2);
                }
                iprot.readListEnd();
              }
              struct.setEmotionListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, GroupEmotion struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.groupId != null) {
        oprot.writeFieldBegin(GROUP_ID_FIELD_DESC);
        oprot.writeString(struct.groupId);
        oprot.writeFieldEnd();
      }
      if (struct.groupName != null) {
        oprot.writeFieldBegin(GROUP_NAME_FIELD_DESC);
        oprot.writeString(struct.groupName);
        oprot.writeFieldEnd();
      }
      if (struct.emotionList != null) {
        oprot.writeFieldBegin(EMOTION_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.emotionList.size()));
          for (EmotionItem _iter3 : struct.emotionList)
          {
            _iter3.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GroupEmotionTupleSchemeFactory implements SchemeFactory {
    public GroupEmotionTupleScheme getScheme() {
      return new GroupEmotionTupleScheme();
    }
  }

  private static class GroupEmotionTupleScheme extends TupleScheme<GroupEmotion> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, GroupEmotion struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetGroupId()) {
        optionals.set(0);
      }
      if (struct.isSetGroupName()) {
        optionals.set(1);
      }
      if (struct.isSetEmotionList()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetGroupId()) {
        oprot.writeString(struct.groupId);
      }
      if (struct.isSetGroupName()) {
        oprot.writeString(struct.groupName);
      }
      if (struct.isSetEmotionList()) {
        {
          oprot.writeI32(struct.emotionList.size());
          for (EmotionItem _iter4 : struct.emotionList)
          {
            _iter4.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, GroupEmotion struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.groupId = iprot.readString();
        struct.setGroupIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.groupName = iprot.readString();
        struct.setGroupNameIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.emotionList = new ArrayList<EmotionItem>(_list5.size);
          for (int _i6 = 0; _i6 < _list5.size; ++_i6)
          {
            EmotionItem _elem7;
            _elem7 = new EmotionItem();
            _elem7.read(iprot);
            struct.emotionList.add(_elem7);
          }
        }
        struct.setEmotionListIsSet(true);
      }
    }
  }

}

