// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Management.proto

package proto.management;

public interface SendClanInfoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:management.SendClanInfo)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>required string clanOwner = 1;</code>
   * @return Whether the clanOwner field is set.
   */
  boolean hasClanOwner();
  /**
   * <code>required string clanOwner = 1;</code>
   * @return The clanOwner.
   */
  String getClanOwner();
  /**
   * <code>required string clanOwner = 1;</code>
   * @return The bytes for clanOwner.
   */
  com.google.protobuf.ByteString
      getClanOwnerBytes();

  /**
   * <code>required bool hasInfo = 2;</code>
   * @return Whether the hasInfo field is set.
   */
  boolean hasHasInfo();
  /**
   * <code>required bool hasInfo = 2;</code>
   * @return The hasInfo.
   */
  boolean getHasInfo();

  /**
   * <code>optional string clanName = 3;</code>
   * @return Whether the clanName field is set.
   */
  boolean hasClanName();
  /**
   * <code>optional string clanName = 3;</code>
   * @return The clanName.
   */
  String getClanName();
  /**
   * <code>optional string clanName = 3;</code>
   * @return The bytes for clanName.
   */
  com.google.protobuf.ByteString
      getClanNameBytes();

  /**
   * <code>optional int32 joinRequirement = 4;</code>
   * @return Whether the joinRequirement field is set.
   */
  boolean hasJoinRequirement();
  /**
   * <code>optional int32 joinRequirement = 4;</code>
   * @return The joinRequirement.
   */
  int getJoinRequirement();

  /**
   * <code>optional int32 kickRequirement = 5;</code>
   * @return Whether the kickRequirement field is set.
   */
  boolean hasKickRequirement();
  /**
   * <code>optional int32 kickRequirement = 5;</code>
   * @return The kickRequirement.
   */
  int getKickRequirement();

  /**
   * <code>optional int32 messageRequirement = 6;</code>
   * @return Whether the messageRequirement field is set.
   */
  boolean hasMessageRequirement();
  /**
   * <code>optional int32 messageRequirement = 6;</code>
   * @return The messageRequirement.
   */
  int getMessageRequirement();

  /**
   * <code>optional int32 lootRequirement = 7;</code>
   * @return Whether the lootRequirement field is set.
   */
  boolean hasLootRequirement();
  /**
   * <code>optional int32 lootRequirement = 7;</code>
   * @return The lootRequirement.
   */
  int getLootRequirement();

  /**
   * <code>repeated .management.SendClanInfo.ClanMember members = 8;</code>
   */
  java.util.List<SendClanInfo.ClanMember>
      getMembersList();
  /**
   * <code>repeated .management.SendClanInfo.ClanMember members = 8;</code>
   */
  SendClanInfo.ClanMember getMembers(int index);
  /**
   * <code>repeated .management.SendClanInfo.ClanMember members = 8;</code>
   */
  int getMembersCount();
  /**
   * <code>repeated .management.SendClanInfo.ClanMember members = 8;</code>
   */
  java.util.List<? extends SendClanInfo.ClanMemberOrBuilder>
      getMembersOrBuilderList();
  /**
   * <code>repeated .management.SendClanInfo.ClanMember members = 8;</code>
   */
  SendClanInfo.ClanMemberOrBuilder getMembersOrBuilder(
      int index);
}
