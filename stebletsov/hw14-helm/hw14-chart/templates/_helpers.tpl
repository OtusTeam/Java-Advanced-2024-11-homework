{{- define "hw14-chart.name" -}}
{{ .Chart.Name }}
{{- end }}

{{- define "hw14-chart.fullname" -}}
{{ .Release.Name }}-{{ .Chart.Name }}
{{- end }}

{{- define "hw14-chart.chart" -}}
{{ .Chart.Name }}-{{ .Chart.Version }}
{{- end }}

{{- define "hw14-chart.labels" -}}
app.kubernetes.io/name: {{ include "hw14-chart.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/version: {{ .Chart.AppVersion }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
app.kubernetes.io/component: web
helm.sh/chart: {{ include "hw14-chart.chart" . }}
{{- end }}

{{- define "hw14-chart.selectorLabels" -}}
app.kubernetes.io/name: {{ include "hw14-chart.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}
