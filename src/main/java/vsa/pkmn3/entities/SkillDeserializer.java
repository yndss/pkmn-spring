package vsa.pkmn3.entities;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import vsa.pkmn3.models.AttackSkill;

import java.io.IOException;
import java.util.Optional;

public class SkillDeserializer extends JsonDeserializer<AttackSkill> {
    @Override
    public AttackSkill deserialize(JsonParser p, DeserializationContext ctxt)       throws IOException, JacksonException {

        JsonNode skillNode = ctxt.readTree(p);

        AttackSkill skill = new AttackSkill();
        skill.setName(skillNode.get("name").asText(""));

        Optional.ofNullable(skillNode.get("description"))
                .ifPresent(
                        jsonNode -> skill.setDescription(jsonNode.asText(""))
                );

        skill.setCost(skillNode.get("cost").asText(""));
        skill.setDamage(skillNode.get("damage").asInt(0));

        return skill;
    }
}