/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package ChatProject;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

/**
 * Status code return
 */
public enum Error implements org.apache.thrift.TEnum {
  SAVE_SUCCESS(1),
  SAVE_FAIL(2),
  READ_DISK_TO_MEM_SUCCESS(3),
  READ_DISK_TO_MEM_FAIL(4),
  WRITE_TO_CACHE_SUCCESS(5),
  WRITE_TO_CACHE_FAIL(6),
  READ_CACHE_FAIL(7),
  WRITE_HASH_FAIL(8),
  WRITE_HASH_SUCCESS(9);

  private final int value;

  private Error(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static Error findByValue(int value) { 
    switch (value) {
      case 1:
        return SAVE_SUCCESS;
      case 2:
        return SAVE_FAIL;
      case 3:
        return READ_DISK_TO_MEM_SUCCESS;
      case 4:
        return READ_DISK_TO_MEM_FAIL;
      case 5:
        return WRITE_TO_CACHE_SUCCESS;
      case 6:
        return WRITE_TO_CACHE_FAIL;
      case 7:
        return READ_CACHE_FAIL;
      case 8:
        return WRITE_HASH_FAIL;
      case 9:
        return WRITE_HASH_SUCCESS;
      default:
        return null;
    }
  }
}
