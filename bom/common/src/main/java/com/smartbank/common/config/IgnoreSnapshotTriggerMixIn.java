package com.smartbank.common.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"snapshotTrigger"})
public abstract class IgnoreSnapshotTriggerMixIn {
}
