{{/* vim: set filetype=mustache: */}}
{{/*
This helper function takes the component name and returns the image name + tag for this component.
*/}}
{{- define "image" -}}
{{- if (hasKey (get .Values.components .name).image "name") -}}
{{- (get .Values.components .name).image.name -}}:{{- (get .Values.components .name).image.tag -}}
{{- else -}}
{{- $mapping := (dict "intelligentTrain" "intelligent-train" "captureApp" "capture-app" "monitoringApp" "monitoring-app" "ceqApp" "ceq-app" "trainController" "train-controller") -}}
image-registry.openshift-image-registry.svc:5000/{{- .Values.namespace -}}/{{- (get $mapping .name) -}}:{{- (get .Values.components .name).image.tag -}}
{{- end -}}
{{- end -}}
{{/*
This helper function takes the component name and returns the image pull policy for this component.
*/}}
{{- define "imagePullPolicy" -}}
{{- if (hasKey (get .Values.components .name).image "pullPolicy") -}}
{{- (get .Values.components .name).image.pullPolicy -}}
{{- else -}}
Always
{{- end -}}
{{- end -}}
