function [H3,P3]=tensorsvd(size,matrix,U1,U2,U3)
M=size(1);
N=size(2);
L=size(3);
T1 = tensor(matrix,[M,N,L]);
P1 = ttm(T1,U1',1);
P2 = ttm(P1,U2',2);
P3 = ttm(P2,U3',3);
H1 = ttm(P3,U1,1);
H2 = ttm(H1,U2,2);
H3 = ttm(H2,U3,3);
%Z = zeros(M,N,L);
%for i=1:L
%    Z(:,:,i)=H3(:,:,i);
%end

