package com.saasquatch.jsonschemainferrer;

import static com.saasquatch.jsonschemainferrer.JunkDrawer.newObject;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Policy for {@code additionalProperties}. Implementations are expected to be stateless and thread
 * safe.
 *
 * @author sli
 * @see AdditionalPropertiesPolicies
 */
@FunctionalInterface
public interface AdditionalPropertiesPolicy extends GenericSchemaFeature {

  /**
   * Get the appropriate {@code additionalProperties} field based on the input. Note that this
   * method should not modify the original input.
   */
  @Nullable
  JsonNode getAdditionalProperties(@Nonnull GenericSchemaAddOnInput input);

  @Override
  default ObjectNode getResult(@Nonnull GenericSchemaAddOnInput input) {
    if (!Consts.Types.OBJECT.equals(input.getType())) {
      return null;
    }
    final JsonNode additionalProps = getAdditionalProperties(input);
    if (additionalProps == null) {
      return null;
    }
    final ObjectNode result = newObject();
    result.set(Consts.Fields.ADDITIONAL_PROPERTIES, additionalProps);
    return result;
  }

}
