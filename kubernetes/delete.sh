kubectl delete pods -n maestro --all 
kubectl delete deployments -n maestro --all
kubectl delete services -n maestro --all
kubectl delete configmaps -n maestro --all
kubectl delete secrets -n maestro --all
kubectl delete namespace -n maestro maestro