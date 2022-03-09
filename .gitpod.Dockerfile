FROM gitpod/workspace-full

ARG KUBECTL_VERSION=v1.23.0

## Install Kubectl
RUN curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl" && \
    chmod +x ./kubectl && \
    sudo mv ./kubectl /usr/local/bin/kubectl && \
    mkdir ~/.kube

## Install Helm
RUN curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 && \
    chmod 700 get_helm.sh && \
    ./get_helm.sh

## Install Kustomize
RUN curl -s "https://raw.githubusercontent.com/kubernetes-sigs/kustomize/master/hack/install_kustomize.sh"  | bash

## Install dependencies
RUN sudo apt update && \
    sudo apt install fzf

## Install easy ctx/ns switcher
RUN git clone https://github.com/blendle/kns.git && \
    cd kns/bin && \
    chmod +x kns && sudo mv kns /usr/local/bin && \
    chmod +x ktx && sudo mv ktx /usr/local/bin

## Install Krew
RUN set -x; cd "$(mktemp -d)" && \
    OS="$(uname | tr '[:upper:]' '[:lower:]')" && \
    ARCH="$(uname -m | sed -e 's/x86_64/amd64/' -e 's/\(arm\)\(64\)\?.*/\1\2/' -e 's/aarch64$/arm64/')" && \
    KREW="krew-${OS}_${ARCH}" && \
    curl -fsSLO "https://github.com/kubernetes-sigs/krew/releases/latest/download/${KREW}.tar.gz" && \
    tar zxvf "${KREW}.tar.gz" && \
    ./"${KREW}" install krew && \
    echo 'export PATH="${KREW_ROOT:-$HOME/.krew}/bin:$PATH"' >> /home/gitpod/.bashrc

## Install Krew main plugins
RUN export PATH="${KREW_ROOT:-$HOME/.krew}/bin:$PATH" && \
    kubectl krew install neat && \
    kubectl krew install access-matrix && \
    kubectl krew install advise-psp && \
    kubectl krew install cert-manager && \
    kubectl krew install ca-cert && \
    kubectl krew install get-all && \
    kubectl krew install ingress-nginx    
    

RUN sudo mkdir /var/lib/pgadmin && \
    sudo chmod 777 /var/lib/pgadmin

# Add aliases
RUN echo 'alias k="kubectl"' >> /home/gitpod/.bashrc

RUN wget -q https://raw.githubusercontent.com/dapr/cli/master/install/install.sh -O - | /bin/bash

# Install sdkman and java 17
RUN bash -c ". /home/gitpod/.sdkman/bin/sdkman-init.sh && sdk install java 17.0.2-zulu"

RUN curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64 && \
    sudo install minikube-linux-amd64 /usr/local/bin/minikube