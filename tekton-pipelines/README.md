# CI pipelines

For more information on how those pipelines are working, see [Build multi-architecture container images with OpenShift, Buildah and Tekton on AWS](https://www.itix.fr/blog/build-multi-architecture-container-images-with-kubernetes-buildah-tekton-aws/).

## Authentication to the registry

```sh
oc create secret docker-registry quay-authentication --docker-email=nmasse@redhat.com --docker-username=nmasse --docker-password=REDACTED --docker-server=quay.io
oc annotate secret/quay-authentication tekton.dev/docker-0=https://quay.io
```
