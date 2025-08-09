{{/*
  Expand the name of the chart.
*/}}
{{- define "insurelytics.name" -}}
{{- .Chart.Name -}}
{{- end -}}

{{/*
  Create a fullname using the chart name and the release name
*/}}
{{- define "insurelytics.fullname" -}}
{{- printf "%s-%s" .Release.Name (include "insurelytics.name" .) | trunc 63 | trimSuffix "-" -}}
{{- end -}}