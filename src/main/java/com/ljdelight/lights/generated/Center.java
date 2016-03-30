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
public class Center implements org.apache.thrift.TBase<Center, Center._Fields>, java.io.Serializable, Cloneable, Comparable<Center> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Center");

  private static final org.apache.thrift.protocol.TField LOCATION_FIELD_DESC = new org.apache.thrift.protocol.TField("location", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField RADIUS_IN_METERS_FIELD_DESC = new org.apache.thrift.protocol.TField("radiusInMeters", org.apache.thrift.protocol.TType.I32, (short)16);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new CenterStandardSchemeFactory());
    schemes.put(TupleScheme.class, new CenterTupleSchemeFactory());
  }

  public Location location; // required
  public int radiusInMeters; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    LOCATION((short)1, "location"),
    RADIUS_IN_METERS((short)16, "radiusInMeters");

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
        case 1: // LOCATION
          return LOCATION;
        case 16: // RADIUS_IN_METERS
          return RADIUS_IN_METERS;
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
  private static final int __RADIUSINMETERS_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.RADIUS_IN_METERS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.LOCATION, new org.apache.thrift.meta_data.FieldMetaData("location", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Location.class)));
    tmpMap.put(_Fields.RADIUS_IN_METERS, new org.apache.thrift.meta_data.FieldMetaData("radiusInMeters", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Center.class, metaDataMap);
  }

  public Center() {
    this.radiusInMeters = 65000;

  }

  public Center(
    Location location)
  {
    this();
    this.location = location;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Center(Center other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetLocation()) {
      this.location = new Location(other.location);
    }
    this.radiusInMeters = other.radiusInMeters;
  }

  public Center deepCopy() {
    return new Center(this);
  }

  @Override
  public void clear() {
    this.location = null;
    this.radiusInMeters = 65000;

  }

  public Location getLocation() {
    return this.location;
  }

  public Center setLocation(Location location) {
    this.location = location;
    return this;
  }

  public void unsetLocation() {
    this.location = null;
  }

  /** Returns true if field location is set (has been assigned a value) and false otherwise */
  public boolean isSetLocation() {
    return this.location != null;
  }

  public void setLocationIsSet(boolean value) {
    if (!value) {
      this.location = null;
    }
  }

  public int getRadiusInMeters() {
    return this.radiusInMeters;
  }

  public Center setRadiusInMeters(int radiusInMeters) {
    this.radiusInMeters = radiusInMeters;
    setRadiusInMetersIsSet(true);
    return this;
  }

  public void unsetRadiusInMeters() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __RADIUSINMETERS_ISSET_ID);
  }

  /** Returns true if field radiusInMeters is set (has been assigned a value) and false otherwise */
  public boolean isSetRadiusInMeters() {
    return EncodingUtils.testBit(__isset_bitfield, __RADIUSINMETERS_ISSET_ID);
  }

  public void setRadiusInMetersIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __RADIUSINMETERS_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case LOCATION:
      if (value == null) {
        unsetLocation();
      } else {
        setLocation((Location)value);
      }
      break;

    case RADIUS_IN_METERS:
      if (value == null) {
        unsetRadiusInMeters();
      } else {
        setRadiusInMeters((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case LOCATION:
      return getLocation();

    case RADIUS_IN_METERS:
      return getRadiusInMeters();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case LOCATION:
      return isSetLocation();
    case RADIUS_IN_METERS:
      return isSetRadiusInMeters();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Center)
      return this.equals((Center)that);
    return false;
  }

  public boolean equals(Center that) {
    if (that == null)
      return false;

    boolean this_present_location = true && this.isSetLocation();
    boolean that_present_location = true && that.isSetLocation();
    if (this_present_location || that_present_location) {
      if (!(this_present_location && that_present_location))
        return false;
      if (!this.location.equals(that.location))
        return false;
    }

    boolean this_present_radiusInMeters = true && this.isSetRadiusInMeters();
    boolean that_present_radiusInMeters = true && that.isSetRadiusInMeters();
    if (this_present_radiusInMeters || that_present_radiusInMeters) {
      if (!(this_present_radiusInMeters && that_present_radiusInMeters))
        return false;
      if (this.radiusInMeters != that.radiusInMeters)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_location = true && (isSetLocation());
    list.add(present_location);
    if (present_location)
      list.add(location);

    boolean present_radiusInMeters = true && (isSetRadiusInMeters());
    list.add(present_radiusInMeters);
    if (present_radiusInMeters)
      list.add(radiusInMeters);

    return list.hashCode();
  }

  @Override
  public int compareTo(Center other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetLocation()).compareTo(other.isSetLocation());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLocation()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.location, other.location);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRadiusInMeters()).compareTo(other.isSetRadiusInMeters());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRadiusInMeters()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.radiusInMeters, other.radiusInMeters);
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
    StringBuilder sb = new StringBuilder("Center(");
    boolean first = true;

    sb.append("location:");
    if (this.location == null) {
      sb.append("null");
    } else {
      sb.append(this.location);
    }
    first = false;
    if (isSetRadiusInMeters()) {
      if (!first) sb.append(", ");
      sb.append("radiusInMeters:");
      sb.append(this.radiusInMeters);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (location == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'location' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
    if (location != null) {
      location.validate();
    }
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

  private static class CenterStandardSchemeFactory implements SchemeFactory {
    public CenterStandardScheme getScheme() {
      return new CenterStandardScheme();
    }
  }

  private static class CenterStandardScheme extends StandardScheme<Center> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Center struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // LOCATION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.location = new Location();
              struct.location.read(iprot);
              struct.setLocationIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 16: // RADIUS_IN_METERS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.radiusInMeters = iprot.readI32();
              struct.setRadiusInMetersIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Center struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.location != null) {
        oprot.writeFieldBegin(LOCATION_FIELD_DESC);
        struct.location.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.isSetRadiusInMeters()) {
        oprot.writeFieldBegin(RADIUS_IN_METERS_FIELD_DESC);
        oprot.writeI32(struct.radiusInMeters);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CenterTupleSchemeFactory implements SchemeFactory {
    public CenterTupleScheme getScheme() {
      return new CenterTupleScheme();
    }
  }

  private static class CenterTupleScheme extends TupleScheme<Center> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Center struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      struct.location.write(oprot);
      BitSet optionals = new BitSet();
      if (struct.isSetRadiusInMeters()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetRadiusInMeters()) {
        oprot.writeI32(struct.radiusInMeters);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Center struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.location = new Location();
      struct.location.read(iprot);
      struct.setLocationIsSet(true);
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.radiusInMeters = iprot.readI32();
        struct.setRadiusInMetersIsSet(true);
      }
    }
  }

}
