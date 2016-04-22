/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.ljdelight.lights.generated;

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

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
public class Meta implements org.apache.thrift.TBase<Meta, Meta._Fields>, java.io.Serializable, Cloneable, Comparable<Meta> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Meta");

  private static final org.apache.thrift.protocol.TField COMMENTS_FIELD_DESC = new org.apache.thrift.protocol.TField("comments", org.apache.thrift.protocol.TType.LIST, (short)1);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new MetaStandardSchemeFactory());
    schemes.put(TupleScheme.class, new MetaTupleSchemeFactory());
  }

  public List<Comment> comments; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    COMMENTS((short)1, "comments");

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
        case 1: // COMMENTS
          return COMMENTS;
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
    tmpMap.put(_Fields.COMMENTS, new org.apache.thrift.meta_data.FieldMetaData("comments", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "Comment"))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Meta.class, metaDataMap);
  }

  public Meta() {
  }

  public Meta(
    List<Comment> comments)
  {
    this();
    this.comments = comments;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Meta(Meta other) {
    if (other.isSetComments()) {
      List<Comment> __this__comments = new ArrayList<Comment>(other.comments.size());
      for (Comment other_element : other.comments) {
        __this__comments.add(other_element);
      }
      this.comments = __this__comments;
    }
  }

  public Meta deepCopy() {
    return new Meta(this);
  }

  @Override
  public void clear() {
    this.comments = null;
  }

  public int getCommentsSize() {
    return (this.comments == null) ? 0 : this.comments.size();
  }

  public java.util.Iterator<Comment> getCommentsIterator() {
    return (this.comments == null) ? null : this.comments.iterator();
  }

  public void addToComments(Comment elem) {
    if (this.comments == null) {
      this.comments = new ArrayList<Comment>();
    }
    this.comments.add(elem);
  }

  public List<Comment> getComments() {
    return this.comments;
  }

  public Meta setComments(List<Comment> comments) {
    this.comments = comments;
    return this;
  }

  public void unsetComments() {
    this.comments = null;
  }

  /** Returns true if field comments is set (has been assigned a value) and false otherwise */
  public boolean isSetComments() {
    return this.comments != null;
  }

  public void setCommentsIsSet(boolean value) {
    if (!value) {
      this.comments = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case COMMENTS:
      if (value == null) {
        unsetComments();
      } else {
        setComments((List<Comment>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case COMMENTS:
      return getComments();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case COMMENTS:
      return isSetComments();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Meta)
      return this.equals((Meta)that);
    return false;
  }

  public boolean equals(Meta that) {
    if (that == null)
      return false;

    boolean this_present_comments = true && this.isSetComments();
    boolean that_present_comments = true && that.isSetComments();
    if (this_present_comments || that_present_comments) {
      if (!(this_present_comments && that_present_comments))
        return false;
      if (!this.comments.equals(that.comments))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_comments = true && (isSetComments());
    list.add(present_comments);
    if (present_comments)
      list.add(comments);

    return list.hashCode();
  }

  @Override
  public int compareTo(Meta other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetComments()).compareTo(other.isSetComments());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetComments()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.comments, other.comments);
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
    StringBuilder sb = new StringBuilder("Meta(");
    boolean first = true;

    sb.append("comments:");
    if (this.comments == null) {
      sb.append("null");
    } else {
      sb.append(this.comments);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (comments == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'comments' was not present! Struct: " + toString());
    }
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

  private static class MetaStandardSchemeFactory implements SchemeFactory {
    public MetaStandardScheme getScheme() {
      return new MetaStandardScheme();
    }
  }

  private static class MetaStandardScheme extends StandardScheme<Meta> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Meta struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // COMMENTS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.comments = new ArrayList<Comment>(_list0.size);
                Comment _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = new Comment();
                  _elem1.read(iprot);
                  struct.comments.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setCommentsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Meta struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.comments != null) {
        oprot.writeFieldBegin(COMMENTS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.comments.size()));
          for (Comment _iter3 : struct.comments)
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

  private static class MetaTupleSchemeFactory implements SchemeFactory {
    public MetaTupleScheme getScheme() {
      return new MetaTupleScheme();
    }
  }

  private static class MetaTupleScheme extends TupleScheme<Meta> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Meta struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      {
        oprot.writeI32(struct.comments.size());
        for (Comment _iter4 : struct.comments)
        {
          _iter4.write(oprot);
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Meta struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      {
        org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.comments = new ArrayList<Comment>(_list5.size);
        Comment _elem6;
        for (int _i7 = 0; _i7 < _list5.size; ++_i7)
        {
          _elem6 = new Comment();
          _elem6.read(iprot);
          struct.comments.add(_elem6);
        }
      }
      struct.setCommentsIsSet(true);
    }
  }

}

